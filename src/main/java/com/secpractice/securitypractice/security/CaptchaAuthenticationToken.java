package com.secpractice.securitypractice.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serial;
import java.util.Collection;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public class CaptchaAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principle;
    private String captcha;


    public CaptchaAuthenticationToken(Object principle, String captcha) {
        super(null);
        this.principle = principle;
        this.captcha = captcha;
        setAuthenticated(false);
    }
    /**
     * Creates a token with the supplied array of authorities.
     */
    public CaptchaAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principle, String captcha) {
        super(authorities);
        this.principle = principle;
        this.captcha = captcha;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.captcha;
    }

    @Override
    public Object getPrincipal() {
        return this.principle;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        captcha = null;
    }
}
