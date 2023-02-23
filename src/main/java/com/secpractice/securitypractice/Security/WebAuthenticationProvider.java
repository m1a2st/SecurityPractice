package com.secpractice.securitypractice.Security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public class WebAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService service;

    public WebAuthenticationProvider(UserDetailsService service) {
        this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credentials = authentication.getCredentials();
        UserDetails userDetails = service.loadUserByUsername(authentication.getName());
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(userDetails, credentials, authentication.getAuthorities());
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
