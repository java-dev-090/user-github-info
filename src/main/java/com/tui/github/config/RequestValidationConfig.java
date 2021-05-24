package com.tui.github.config;

import com.tui.github.interceptor.RequestValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestValidationConfig implements WebMvcConfigurer {

    private final RequestValidationInterceptor requestInterceptor;

    public RequestValidationConfig(RequestValidationInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registery) {
        registery.addInterceptor(requestInterceptor);
    }

}
