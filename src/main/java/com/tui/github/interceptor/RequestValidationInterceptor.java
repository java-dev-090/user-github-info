package com.tui.github.interceptor;

import com.tui.github.utils.ExceptionConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
            throws HttpMediaTypeNotAcceptableException {
        String accept=request.getHeader(HttpHeaders.ACCEPT);
        if(request.getRequestURI().contains("swagger")) {
            return true;
        }
        if(!MediaType.APPLICATION_JSON_VALUE.equals(accept)) {
            throw new HttpMediaTypeNotAcceptableException(ExceptionConstant.APPLICATION_JSON_EXCEPTION);
        }
        return true;
    }

}
