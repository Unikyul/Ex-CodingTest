package com.example.demo.core.fliter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class UserFliter implements Filter {

    private static final String FORBIDDEN_SPECIAL_CHARS = "[^a-zA-Z0-9?&=:/]";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI() + (httpRequest.getQueryString() != null ? "?" + httpRequest.getQueryString() : "");

        // 특수문자 필터링
        if (Pattern.compile(FORBIDDEN_SPECIAL_CHARS).matcher(requestURI).find()) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "특수문자가 포함된 URL은 허용되지 않습니다.");
            return;
        }

        // 필터 체인을 계속 실행
        chain.doFilter(request, response);
    }
}
