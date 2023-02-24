package com.secpractice.securitypractice.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description 設定攔截驗證碼的請求為
 *              POST /login?phone={號碼}&captcha={驗證碼}
 *              Host: localhost:8080
 */
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FROM_PHONE_KEY = "phone";
    public static final String SPRING_SECURITY_FROM_CAPTCHA_KEY = "captcha";
    public CaptchaAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if("POST".equals(request.getMethod())){
            throw new AuthenticationServiceException("Authentication method not support" + request.getMethod());
        }
        String phone = obtainPhone(request).orElse("").trim();
        String captcha = obtainCaptcha(request).orElse("");

        CaptchaAuthenticationToken authRequest = new CaptchaAuthenticationToken(phone, captcha);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, CaptchaAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    private Optional<String> obtainCaptcha(HttpServletRequest request) {
        return Optional.of(request.getParameter(SPRING_SECURITY_FROM_PHONE_KEY));
    }

    private Optional<String> obtainPhone(HttpServletRequest request) {
        return Optional.of(request.getParameter(SPRING_SECURITY_FROM_CAPTCHA_KEY));
    }
}
