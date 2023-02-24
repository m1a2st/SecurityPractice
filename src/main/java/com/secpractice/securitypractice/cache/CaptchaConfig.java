package com.secpractice.securitypractice.cache;

import com.secpractice.securitypractice.security.CaptchaAuthenticationFilter;
import com.secpractice.securitypractice.security.CaptchaAuthenticationProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collections;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
@Log4j2
@EnableWebSecurity
public class CaptchaConfig {

    @Bean
    @Qualifier("captchaUserDetailService")
    public UserDetailsService captchaUserDetailService(){
        // 驗證碼登入後密碼無意義但是需要填充
        return username -> User.withUsername(username).password("TEMP")
                // TODO 注入權限
                .authorities(AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_APP")).build();
    }

    /**
     * 驗證碼確認器
     * @param service the user details service
     * @param captchaService the captcha service
     * @return CaptchaAuthenticationProvider
     */
    @Bean
    public CaptchaAuthenticationProvider captchaAuthenticationProvider(
                            @Qualifier("captchaUserDetailService") UserDetailsService service,
                            CaptchaService captchaService){
        return new CaptchaAuthenticationProvider(service,captchaService);

    }

    @Bean
    public CaptchaAuthenticationFilter captchaAuthenticationFilter(AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                   AuthenticationFailureHandler authenticationFailureHandler,
                                                                   CaptchaAuthenticationProvider captchaAuthenticationProvider){
        CaptchaAuthenticationFilter captchaAuthenticationFilter = new CaptchaAuthenticationFilter();
        // 配置 authenticationManager
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(captchaAuthenticationProvider));
        captchaAuthenticationFilter.setAuthenticationManager(providerManager);
        // 配置 成功處理器
        captchaAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // 配置 失敗處理器
        captchaAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return captchaAuthenticationFilter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (request, response, authentication) -> {

        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, authentication) -> {

        };
    }

}
