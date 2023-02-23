package com.secpractice.securitypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
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
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationSuccessHandler customerASHandler;
    private final AuthenticationFailureHandler customerAFHandler;
    private final LogoutHandler customerLogoutHandler;
    private final LogoutSuccessHandler customerLogoutSuccessHandler;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthenticationFailureHandler customerAFHandler,
                          AuthenticationSuccessHandler customerASHandler,
                          LogoutHandler customerLogoutHandler,
                          LogoutSuccessHandler customerLogoutSuccessHandler, UserDetailsService userDetailsService) {
        super();
        this.customerAFHandler = customerAFHandler;
        this.customerASHandler = customerASHandler;
        this.customerLogoutHandler = customerLogoutHandler;
        this.customerLogoutSuccessHandler = customerLogoutSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiLoginFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/login")
                .csrf().disable()
                .cors()
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
                .invalidateHttpSession(true)
                .addLogoutHandler(customerLogoutHandler)
                .logoutSuccessHandler(customerLogoutSuccessHandler);
        return http.build();
    }

    @Bean
    public SecurityFilterChain fromLoginFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/login/v1")
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successForwardUrl("/index")
                .failureForwardUrl("/logout")
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(customerLogoutHandler())
                .logoutSuccessHandler(customerLogoutSuccessHandler());
        return http.build();
    }

    public LogoutHandler customerLogoutHandler() {
        return (request, response, authentication) -> {

        };
    }

    @Bean
    public LogoutSuccessHandler customerLogoutSuccessHandler() {
        return (request, response, authentication) -> {

        };
    }

    @Bean
    public AuthenticationProvider customerAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
