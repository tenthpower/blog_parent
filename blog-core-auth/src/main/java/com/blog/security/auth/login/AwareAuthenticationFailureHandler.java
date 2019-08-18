package com.blog.security.auth.login;

import com.blog.entity.Result;
import com.blog.entity.StatusCode;
import com.blog.security.exceptions.AuthMethodNotSupportedException;
import com.blog.security.exceptions.ExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  权限认证失败的Handler
 */
@Component
public class AwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), new Result(false, StatusCode.UNAUTHORIZED,"Invalid username or password"));
        } else if (e instanceof ExpiredTokenException) {
            mapper.writeValue(response.getWriter(), new Result(false, StatusCode.UNAUTHORIZED,"Token has expired"));
        } else if (e instanceof AuthMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(),  new Result(false, StatusCode.UNAUTHORIZED,e.getMessage()));
        } else {
            mapper.writeValue(response.getWriter(), new Result(false, StatusCode.UNAUTHORIZED,"Authentication failed"));
        }

    }
}
