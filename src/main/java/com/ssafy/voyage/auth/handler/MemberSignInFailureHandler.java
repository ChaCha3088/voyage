package com.ssafy.voyage.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.voyage.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MemberSignInFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    public MemberSignInFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Response responseClass = Response.builder()
            .cause(exception.getClass().getSimpleName())
            .message(exception.getMessage())
            .build();

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseClass));
    }
}
