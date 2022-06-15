package io.github.hlg212.fcf.web.filter;

import io.github.hlg212.fcf.log.LoggingParam;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;


public class LoggingParamFilter implements Filter {

    @Autowired(required = false)
    private List<LoggingParam> params;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        try {
            putParams();
            chain.doFilter(request, response);
        } finally {
            removeParams();
        }
    }

    private void putParams()
    {
        for( LoggingParam p : params )
        {
            MDC.put( p.getKey(),p.getValue() );
        }
    }

    private void removeParams()
    {
        for( LoggingParam p : params )
        {
            MDC.remove( p.getKey() );
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
