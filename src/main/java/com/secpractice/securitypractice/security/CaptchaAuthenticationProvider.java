package com.secpractice.securitypractice.security;

import com.secpractice.securitypractice.cache.CaptchaService;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public class CaptchaAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final UserDetailsService userDetailsService;
    private final CaptchaService captchaService;
    private final MessageSourceAccessor message = SpringSecurityMessageSource.getAccessor();

    public CaptchaAuthenticationProvider(UserDetailsService userDetailsService, CaptchaService captchaService) {
        this.userDetailsService = userDetailsService;
        this.captchaService = captchaService;
    }

    @PostConstruct
    public void init() {
        //Bean的初始化邏輯
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(CaptchaAuthenticationToken.class, authentication,
                () -> message.getMessage(
                        "CaptchaAuthenticationProvider.onlySupports",
                        "Only CaptchaAuthenticationToken is supported"));
        CaptchaAuthenticationToken unAuthenticationToken = (CaptchaAuthenticationToken) authentication;
        String phone = unAuthenticationToken.getName();
        String rawCode = (String) unAuthenticationToken.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        // 此處省略對UserDetails 的可用性 是否過期  是否鎖定 是否失效的檢驗
        // 建議根據實際情況添加  或者在 UserDetailsService 的實現中處理
        if (Objects.isNull(userDetails)) {
            throw new BadCredentialsException("Bad Credential");
        }

        if (captchaService.verifyCaptcha(phone, rawCode)) {
            return createSuccessAuthentication(authentication, userDetails);
        } else {
            throw new BadCredentialsException("captcha is not match");
        }
    }

    /**
     * 認證成功將非授信憑據轉成授信憑據
     * 封裝用戶訊息，角色訊息
     * @param authentication the authentication
     * @param user the user
     * @return the authentication
     */
    private Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        Collection<? extends GrantedAuthority> grantedAuthorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        CaptchaAuthenticationToken token = new CaptchaAuthenticationToken(grantedAuthorities, user, null);
        token.setDetails(authentication.getDetails());
        return token;
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return CaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
