package com.hlg.fcf.web.filter;

import com.hlg.fcf.util.ThreadLocalHelper;

import javax.servlet.*;
import java.io.IOException;

/**
 * @program: frame-parent
 * @description: ${description}
 * @author: huangligui
 * @create: 2018-10-26 15:49
 **/
public class ThreadLocalFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        try {
            chain.doFilter(request, response);
        } finally {
            ThreadLocalHelper.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
