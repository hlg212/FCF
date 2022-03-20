package com.hlg.fcf.conf;

import com.hlg.fcf.annotation.SqlLogConditional;
import com.hlg.fcf.annotation.TransactionLogConditional;
import com.hlg.fcf.interceptor.TransactionLogInterceptor;
import com.hlg.fcf.interceptor.TransactionSqlLogInterceptor;
import com.hlg.fcf.log.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@TransactionLogConditional
public class TransactionLogConfig {

    @Bean
    public TransactionLogInterceptor transactionLogInterceptor()
    {
        return new TransactionLogInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionLogWrite transactionLogWrite()
    {
        return new EsbTransactionLogWrite();
    }

    @Configuration
    @SqlLogConditional
    public static class TransactionSqlLogConfig {
        @Bean
        public TransactionSqlLogInterceptor transactionSqlLogInterceptor()
        {
            return new TransactionSqlLogInterceptor();
        }

        @Bean
        @ConditionalOnMissingBean
        public TransactionSqlLogWrite transactionSqlLogWrite()
        {
            return new EsbTransactionSqlLogWrite();
        }

        @Bean
        public SqlLogWrite transactionSqlLogPutWrite()
        {
            return new TransactionSqlLogInterceptor.TransactionSqlLogPutWrite();
        }
    }

}
