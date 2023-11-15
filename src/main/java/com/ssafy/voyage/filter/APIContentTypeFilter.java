package com.ssafy.voyage.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@Component
public class APIContentTypeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // "/api"로 시작하지 않으면
        if (request.getRequestURI().startsWith("/api")) {
            // 다음 필터 호출 X
            // Content-Type이 null이거나 application/json이 아니면
            if (request.getContentType() == null || !request.getContentType().contains("application/json")) {
                // 400 Bad Request
                response.setStatus(SC_BAD_REQUEST);

                // 다음 필터 호출 X

            }
            return;
        }



        filterChain.doFilter(request, response);
    }
}
