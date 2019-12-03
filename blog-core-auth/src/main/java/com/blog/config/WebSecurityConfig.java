package com.blog.config;

import com.blog.security.RestAuthenticationEntryPoint;
import com.blog.security.auth.login.LoginAuthenticationProvider;
import com.blog.security.auth.login.LoginProcessingFilter;
import com.blog.security.auth.token.SkipPathRequestMatcher;
import com.blog.security.auth.token.TokenAuthenticationProcessingFilter;
import com.blog.security.auth.token.TokenAuthenticationProvider;
import com.blog.security.auth.token.extractor.TokenExtractor;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @author Levin
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final LoginAuthenticationProvider loginAuthenticationProvider;
    private final TokenAuthenticationProvider tokenAuthenticationProvider;
    private final TokenExtractor tokenExtractor;

    @Autowired
    public WebSecurityConfig(RestAuthenticationEntryPoint authenticationEntryPoint, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, LoginAuthenticationProvider loginAuthenticationProvider, TokenAuthenticationProvider tokenAuthenticationProvider, TokenExtractor tokenExtractor) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.loginAuthenticationProvider = loginAuthenticationProvider;
        this.tokenAuthenticationProvider = tokenAuthenticationProvider;
        this.tokenExtractor = tokenExtractor;
    }

    private LoginProcessingFilter buildLoginProcessingFilter() throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter("/api/auth/login", successHandler, failureHandler);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    private TokenAuthenticationProcessingFilter buildTokenAuthenticationProcessingFilter() throws Exception {
        List<String> list = Lists.newArrayList("/api/**", "/management/**");
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(list);
        TokenAuthenticationProcessingFilter filter = new TokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 因为使用的是JWT，因此这里可以关闭csrf了
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and() // 以下不需要认证
                    .authorizeRequests()
                    .antMatchers("/api/auth/login").permitAll() // 登陆
                    .antMatchers("/api/auth/refresh_token").permitAll() // 刷新token
                .and() // 以下需要认证
                    .authorizeRequests()
                    .antMatchers("/api/**").authenticated() //
                    .antMatchers("/management/**").hasAnyRole("ROLE_ADMIN")
                .and()
                    .addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(buildTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
