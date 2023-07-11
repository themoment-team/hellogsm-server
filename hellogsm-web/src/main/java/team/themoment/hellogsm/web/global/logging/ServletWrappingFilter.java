package team.themoment.hellogsm.web.global.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ServletWrappingFilter implements Filter {

    // HttpServletRequest, HttpServletResponse의 body 값을 여러번 읽을 수 있도록 하는 filter
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper((HttpServletRequest) req);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper((HttpServletResponse) res);
        chain.doFilter(wrappingRequest, wrappingResponse);
    }
}
