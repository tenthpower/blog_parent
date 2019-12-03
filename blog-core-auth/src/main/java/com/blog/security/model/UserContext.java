package com.blog.security.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author 和彦鹏
 */
public class UserContext {

    private final String userName;

    private final List<GrantedAuthority> authorities;

    private UserContext(String userName, List<GrantedAuthority> authorities) {
        this.userName = userName;
        this.authorities = authorities;
    }

    public static UserContext create(String userName, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(userName)) throw new IllegalArgumentException("Username is blank: " + userName);
        return new UserContext(userName, authorities);
    }

    public String getUserName() {
        return userName;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
