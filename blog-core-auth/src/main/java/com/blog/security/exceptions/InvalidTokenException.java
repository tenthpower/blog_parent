package com.blog.security.exceptions;

/**
 * 无效的Token
 * @author 和彦鹏
 */
public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = -294671188037098603L;

    public InvalidTokenException(String msg) {
        super(msg);
    }

    public InvalidTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
