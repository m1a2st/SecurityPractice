package com.secpractice.securitypractice.config;

import com.secpractice.securitypractice.security.CaptchaAuthenticationFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private final CaptchaAuthenticationFilter captchaAuthenticationFilter;

    public SecurityConfig(CaptchaAuthenticationFilter captchaAuthenticationFilter) {
        this.captchaAuthenticationFilter = captchaAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(captchaAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
