package com.example.demo.core.config;

import com.example.demo.core.fliter.UserFliter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<UserFliter> userFilterRegistration() {
        FilterRegistrationBean<UserFliter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFliter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL에 대해 필터 적용
        return registrationBean;
    }
}