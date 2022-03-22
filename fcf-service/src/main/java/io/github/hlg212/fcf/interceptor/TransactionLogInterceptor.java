package  io.github.hlg212.fcf.interceptor;

import com.alibaba.fastjson.JSON;
import  io.github.hlg212.fcf.annotation.EnableTransactionLog;
import  io.github.hlg212.fcf.constants.DataConstants;
import  io.github.hlg212.fcf.context.TransactionContext;
import  io.github.hlg212.fcf.log.TransactionLogWrite;
import  io.github.hlg212.fcf.model.basic.IUser;
import  io.github.hlg212.fcf.model.log.ITransactionLog;
import  io.github.hlg212.fcf.model.log.TransactionLog;
import  io.github.hlg212.fcf.service.impl.ServiceWapper;
import  io.github.hlg212.fcf.util.*;
import io.opentracing.ActiveSpan;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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

/**
 * ClassName: TransactionLogInterceptor
 * Function:  记录事务日志
 * Reason: TODO ADD REASON(可选).
 * date: 2019年5月6日 上午9:10:42
 *
 * @author huangligui
 */

@Aspect
@Slf4j
public class TransactionLogInterceptor  implements Ordered {

    private final static String HOLDER_KEY = "TRANSACTIONLOG_HOLDER";

    private LogTransactionSynchronizationAdapter transactionSynchronizationAdapter = new LogTransactionSynchronizationAdapter();

    private TransactionContext currTransactionContext = new CurrTransactionContext();


    @Autowired
    private TransactionLogWrite transactionLogWrite;

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void method() {
    }

    @Before(value="method()")
    public void before(JoinPoint joinPoint){
    	if(!isRecodeLog(joinPoint)) {
    		return ;
    	}
        MethodSignature methodSignature =  (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object v = ThreadLocalHelper.get(HOLDER_KEY);
        if( v == null )
        {
            if ( TransactionSynchronizationManager.isSynchronizationActive() ) {
                String businessMethod = getBusinessMethodPath(method,joinPoint.getTarget());

                Holder holder = createHolder(businessMethod);

                holder.log.setParams( JSON.toJSONString( joinPoint.getArgs()) );

                ThreadLocalHelper.set(HOLDER_KEY,holder);
                TransactionContextHelper.setContext(currTransactionContext);
               //
                TransactionSynchronizationManager.registerSynchronization( this.transactionSynchronizationAdapter );
            }
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
		EnableTransactionLog serviceTransactionLog = serviceImplClass.getAnnotation(EnableTransactionLog.class);
		boolean serviceRecode = serviceTransactionLog == null ? true : serviceTransactionLog.value();
		return methodRecode && serviceRecode;
	}
    

    private String getBusinessMethodPath(Method method,Object service){
        if(service instanceof  ServiceWapper ){
            Class<?> serviceClass = ((ServiceWapper) service).getServiceClass();
            return serviceClass.getName() + "." + method.getName();
        }
        return TransactionSynchronizationManager.getCurrentTransactionName();
    }

    @AfterThrowing(value="method()",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
        log.debug("{}","afterThrowing");
        Holder holder = getHolder();
        if( holder != null){
            holder.ex = e;
        }
    }

    private ActiveSpan createSpan(String name)
    {
        Tracer tracer = new SkywalkingTracer();
        Tracer.SpanBuilder spanBuilder = tracer.buildSpan(name);
        spanBuilder.asChildOf( tracer.activeSpan() );
        ActiveSpan span =  spanBuilder.startActive();

        return span;
    }


    private void completion(String state)
    {
        log.debug("completion:{},state:{}",getName(),state);
        TransactionLog alog = getLog();
        ActiveSpan span =  getSpan();

        alog.setEndTime( new Date() );
        long duration =  alog.getEndTime().getTime() -  alog.getStartTime().getTime();
        alog.setDuration(duration);
        alog.setState(state);
        Exception ex = getEx();
        if( ex != null )
        {
            Map m = new LinkedHashMap();
            m.put("error.kind",ex.getClass().getName());
            m.put("message",ex.getMessage());
            String stack = StackTraceHelper.getSignificantStackTrace(ex);
            m.put("stack",stack);
            span.log(m);
            alog.setExceptionStack(stack);
            alog.setExceptionMess(ex.getMessage());
            span.setTag("error",true);
        }

        //span.setTag("isError",true);

        //org.apache.skywalking.apm.toolkit.trace.ActiveSpan.tag("is error","true");
        //org.apache.skywalking.apm.toolkit.trace.ActiveSpan.tag("isError","true");
        span.close();
        try{
            transactionLogWrite.write(alog);
        }
       catch (Exception e)
       {
           log.error("保存事务日志出错!",e);
       }
    }

    private TransactionLog create(String operationName)
    {
        TransactionLog log = new TransactionLog();
        log.setStartTime(new Date());
        log.setTraceId(TraceContext.traceId());
        log.setAccessId(AccessContextHelper.getAccessId());
        IUser user = FworkHelper.getUser();
        if( user != null )
        {
            log.setUserId(user.getId());
            log.setUserName(user.getName());
        }

        log.setOperationName(operationName);
        log.setId(createId());
        log.setAppCode(AppContextHelper.getAppCode());
        return log;
    }

    private String createId()
    {
       return DateFormatUtils.format(new Date(),"yyyyMM") + UUID.randomUUID().toString().replaceAll("-","");
    }


    @Override
    public int getOrder() {
        return 100;
    }

    private Holder createHolder(String transactionName)
    {
        Holder holder = new Holder(create(transactionName),createSpan(transactionName),transactionName);
        return holder;
    }

    private TransactionLog getLog()
    {
        Holder holder = getHolder();
        if(holder == null) {
        	return null;
        }
        return holder.log;
    }
    private ActiveSpan getSpan()
    {
        Holder holder = getHolder();
        return holder.span;
    }
    private String getName()
    {
        Holder holder = getHolder();
        return holder.name;
    }

    private Exception getEx()
    {
        Holder holder = getHolder();
        return holder.ex;
    }
    private Holder getHolder()
    {
       return (Holder)ThreadLocalHelper.get(HOLDER_KEY);
    }


    private class Holder
    {
        private TransactionLog log;
        private ActiveSpan span;
        private String name;
        private Exception ex;

        Holder(TransactionLog log,ActiveSpan span,String name)
        {
            this.log = log;
            this.span = span;
            this.name = name;
        }
    }

    private class CurrTransactionContext implements TransactionContext
    {
        @Override
        public ITransactionLog getLog() {
            return TransactionLogInterceptor.this.getLog();
        }
    }


    private class LogTransactionSynchronizationAdapter extends  TransactionSynchronizationAdapter
    {
        private Stack stack = new Stack();

        @Override
        public void afterCompletion(int status) {
            log.debug( "afterCompletion:{},status:{}",status);

            String state = DataConstants.Y;
            if(   TransactionSynchronization.STATUS_ROLLED_BACK == status
                    ||   TransactionSynchronization.STATUS_UNKNOWN == status )
            {
                state = DataConstants.N;
            }

            completion(state);
            ThreadLocalHelper.del(HOLDER_KEY);
        }

        @Override
        public void resume() {
            log.debug("{}","resume");
            Object v = stack.pop();
            ThreadLocalHelper.set(HOLDER_KEY,v);
        }

        @Override
        public void suspend() {
            log.debug("{}","suspend");
            Object v = ThreadLocalHelper.get(HOLDER_KEY);
            stack.push(v);
            ThreadLocalHelper.del(HOLDER_KEY);
        }
    }

}
