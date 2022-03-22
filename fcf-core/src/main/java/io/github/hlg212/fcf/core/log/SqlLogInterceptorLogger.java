package  io.github.hlg212.fcf.core.log;

import  io.github.hlg212.fcf.log.SqlLogWrite;
import  io.github.hlg212.fcf.util.SpringHelper;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.Slf4JLogger;
import org.apache.commons.lang.StringUtils;

/**
 *  通过 p6spy 拦截sql
 *
 * @date 2019年5月8日 上午8:50:16
 *
 * @author huangligui
 */
public class SqlLogInterceptorLogger extends Slf4JLogger {

    private static SqlLogWrite sqlLogWrite;

    private static boolean sqlLogWriteFlag = false;

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {

        if( StringUtils.isNotEmpty(sql) ){
            if( !sql.startsWith("select") ) {
                if ( getSqlLogWrite() != null ) {
                    sqlLogWrite.write(sql);
                }
            }
        }

        super.logSQL(connectionId, now, elapsed, category, prepared, sql, url);
    }

    private SqlLogWrite getSqlLogWrite()
    {
        if( !sqlLogWriteFlag && sqlLogWrite == null )
        {
            sqlLogWrite = SpringHelper.getBean(SqlLogWrite.class);
            sqlLogWriteFlag = true;
        }
        return sqlLogWrite;
    }
}
