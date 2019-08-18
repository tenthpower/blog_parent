package com.blog.security.model.token;

/**
 * Token 接口，被获取token，刷新token使用
 * @author 和彦鹏
 */
public interface Token {

    /**
     * 获取 Token
     *
     * @return Token
     */
    String getToken();
}
