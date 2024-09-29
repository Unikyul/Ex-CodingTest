package com.example.demo.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AopLoggingAspect {

    // UserController의 모든 메서드에 대해 AOP로 로깅 처리
    @Before("execution(* com.example.demo.user.UserController.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            String clientAgent = request.getHeader("User-Agent");
            // Client Agent와 호출된 메서드 로깅
            System.out.printf("Client Agent: %s, Called Method: %s%n", clientAgent, joinPoint.getSignature().getName());
        }
    }

    // 현재 HttpServletRequest 가져오기
    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
