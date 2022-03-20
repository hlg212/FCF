package com.hlg.fcf.interceptor;

import com.hlg.fcf.annotation.EnableTransactionLog;
import com.hlg.fcf.log.SqlLogWrite;
import com.hlg.fcf.log.TransactionSqlLogWrite;
import com.hlg.fcf.model.log.ISqlLog;
import com.hlg.fcf.model.log.ITransactionLog;
import com.hlg.fcf.model.log.SqlLog;
import com.hlg.fcf.model.log.TransactionSqlLog;
import com.hlg.fcf.util.TransactionContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: TransactionLogInterceptor
 * Function:  记录事务中的sql日志,成功才进行保存
 * Reason: TODO ADD REASON(可选).
 * date: 2019年5月6日 上午9:10:42
 *
 * @author huangligui
 */

@Aspect
@Slf4j
public class TransactionSqlLogInterceptor implements Ordered {


    private SqlLogTransactionSynchronizationAdapter transactionSynchronizationAdapter = new SqlLogTransactionSynchronizationAdapter();

    private static Map<String,Collection> tsqlsMap = new ConcurrentHashMap();

    @Autowired
    private TransactionSqlLogWrite transactionSqlLogWrite;

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void method() {
    }

    @Before(value="method()")
    public void before(JoinPoint joinPoint){
    	if(!isRecodeLog(joinPoint)) {
    		return ;
    	}
        if ( TransactionSynchronizationManager.isSynchronizationActive() ) {
            TransactionSynchronizationManager.registerSynchronization( this.transactionSynchronizationAdapter );
        }
    }
    
    private boolean isRecodeLog(JoinPoint joinPoint) {
		MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Class<?> serviceImplClass = AopUtils.getTargetClass(joinPoint.getTarget());
		if(!serviceImplClass.equals(method.getDeclaringClass())) {
			try {
				method = serviceImplClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
			} catch (NoSuchMethodException | SecurityException  e) {
				
			}
		}
		EnableTransactionLog methodTransactionLog = method.getAnnotation(EnableTransactionLog.class);
		boolean methodRecode = methodTransactionLog == null ? true : methodTransactionLog.value();
		EnableTransactionLog serviceTransactionLog = method.getDeclaredAnnotation(EnableTransactionLog.class);
		boolean serviceRecode = serviceTransactionLog == null ? true : serviceTransactionLog.value();
		return methodRecode || serviceRecode;
	}
    

    @Override
    public int getOrder() {
        return 100;
    }


    private class SqlLogTransactionSynchronizationAdapter extends  TransactionSynchronizationAdapter
    {

        @Override
        public void afterCompletion(int status) {
            log.info( "afterCompletion:{},status:{}",status);
            ITransactionLog tlog =  TransactionContextHelper.getLog();
            if(tlog == null) {
            	return ;
            }
            Collection sqls = tsqlsMap.remove(tlog.getId());

            if(   TransactionSynchronization.STATUS_ROLLED_BACK == status
                    ||   TransactionSynchronization.STATUS_UNKNOWN == status )
            {
                return ;
            }

            TransactionSqlLog tsqllog = create(tlog,sqls);
            try{
                transactionSqlLogWrite.write(tsqllog);
            }catch (Exception e)
            {
                log.error("保存sql日志出错!",e);
            }
        }
    }

    private TransactionSqlLog create(ITransactionLog tlog,Collection sqls)
    {
        TransactionSqlLog tsqllog = new TransactionSqlLog();
        tsqllog.setSqllogs(sqls);
        tsqllog.setTransactionId(tlog.getId());
        tsqllog.setStartTime(tlog.getStartTime());
        tsqllog.setUserId(tlog.getUserId());
        tsqllog.setUserName(tlog.getUserName());
        tsqllog.setAppCode(tlog.getAppCode());


        return tsqllog;

    }

    public static class TransactionSqlLogPutWrite implements SqlLogWrite
    {
        private static Set opSqls = new HashSet();
        static {
                opSqls.add("INSERT");
                opSqls.add("UPDATE");
                opSqls.add("DELETE");
        }


        @Override
        public void write(String sql) {

            String opSql =  sql.substring(0,sql.indexOf(" ") ).trim();
            if( !opSqls.contains(opSql.toUpperCase()) )
            {
                return;
            }
            ITransactionLog tlog =  TransactionContextHelper.getLog();
            if( tlog == null ) {
                return;
            }
            Collection sqls = tsqlsMap.get(tlog.getId());
            if( sqls == null )
            {
                sqls = new ArrayList();
                tsqlsMap.put(tlog.getId(),sqls);
            }
            sqls.add(createSqlLog(sql,tlog.getId()));
        }

        private ISqlLog createSqlLog(String sql,String tid)
        {
            SqlLog log = new SqlLog();
            log.setCreateTime(new Date());
            log.setSql(sql);
            log.setTransactionId(tid);
            return log;
        }
    }



}
