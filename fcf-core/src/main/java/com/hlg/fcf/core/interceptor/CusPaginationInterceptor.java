package com.hlg.fcf.core.interceptor;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.plugin.Invocation;

public class CusPaginationInterceptor extends PaginationInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {


        return super.intercept(invocation);
    }
}
