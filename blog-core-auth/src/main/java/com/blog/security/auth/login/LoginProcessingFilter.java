package com.blog.security.auth.login;

import com.blog.security.exceptions.AuthMethodNotSupportedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录处理过滤器
 *
 * @描述：AbstractAuthenticationProcessingFilter是一个抽象类，主要的功能是身份认证OAuth2ClientAuthenticationProcessingFilter（Spriing OAuth2）、
 *  RememberMeAuthenticationFilter（RememberMe）都继承了 AbstractAuthenticationProcessingFilter,并重写了方法attemptAuthentication
 *  进行身份认证。
 * @author 和彦鹏
 */
public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger logger = LoggerFactory.getLogger(LoginProcessingFilter.class);

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public LoginProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                 AuthenticationFailureHandler failureHandler) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    /**
     * @描述 这个方法的目的很明确，就是需要子类提供身份认证的具体实现。子类根据 HttpServletRequest 等信息进行身份认证，
     *      并返回 Authentication 对象：表示认证成功返回的身份认证信息
     *      null： 表示需要其他 Filter 继续进行身份认证
     *      异常： 表示认证失败。
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: " + request.getMethod());
            }
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
