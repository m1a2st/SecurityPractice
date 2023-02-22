package com.secpractice.securitypractice.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler customerASHandler;
    private final AuthenticationFailureHandler customerAFHandler;
    private final LogoutHandler customerLogoutHandler;
    private final LogoutSuccessHandler customerLogoutSuccessHandler;
    private final AccessDeniedHandler customerADHandler;

    private final AuthenticationEntryPoint customerAEPoint;

    public SecurityConfig(AuthenticationFailureHandler customerAFHandler,
                          AuthenticationSuccessHandler customerASHandler,
                          LogoutHandler customerLogoutHandler,
                          LogoutSuccessHandler customerLogoutSuccessHandler,
                          AccessDeniedHandler customerADHandler,
                          AuthenticationEntryPoint customerAEPoint) {
        super();
        this.customerAFHandler = customerAFHandler;
        this.customerASHandler = customerASHandler;
        this.customerLogoutHandler = customerLogoutHandler;
        this.customerLogoutSuccessHandler = customerLogoutSuccessHandler;
        this.customerADHandler = customerADHandler;
        this.customerAEPoint = customerAEPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customerADHandler)
                .authenticationEntryPoint(customerAEPoint)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/login", "/login/failure").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(customerASHandler)
                .failureHandler(customerAFHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .deleteCookies("CookieName")
                .invalidateHttpSession(true)
                .addLogoutHandler(customerLogoutHandler)
                .logoutSuccessHandler(customerLogoutSuccessHandler);
        return http.build();
    }
}
