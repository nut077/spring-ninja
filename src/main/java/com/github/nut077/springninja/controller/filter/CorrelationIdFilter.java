package com.github.nut077.springninja.controller.filter;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Log4j2
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private final String KEY = "x-Correlation-Id";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        // check servlet path contain /api/v**
        if (StringUtils.countOccurrencesOf(request.getServletPath(), "/api/v") > 0) {
            log.info("doFilterInternal");

            // check correlation-id from request header, if not found system will Generate
            String correlationId = "xxx-" +
                    (StringUtils.isEmpty(
                            request.getHeader(KEY))
                            ? UUID.randomUUID().toString().toLowerCase().substring(0, 8)
                            : request.getHeader(KEY));

            // set correlation-id to response header
            response.setHeader(KEY, correlationId);

            // set correlation-id to log4j2
            ThreadContext.put("c-id", correlationId);

            // Pass to Spring MVC dispatcher
            filterChain.doFilter(request, response);

            // Release all resources
            ThreadContext.clearAll();
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
