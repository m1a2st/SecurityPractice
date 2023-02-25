package com.secpractice.securitypractice.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public class SsoException extends UsernameNotFoundException {
    public SsoException(String msg) {
        super(msg);
    }

    public SsoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
