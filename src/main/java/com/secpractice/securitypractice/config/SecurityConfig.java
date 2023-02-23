package com.secpractice.securitypractice.config;

import com.secpractice.securitypractice.Security.LoginUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    private final LogoutHandler customerLogoutHandler;
    private final LogoutSuccessHandler customerLogoutSuccessHandler;
    private final AuthenticationSuccessHandler customerASHandler;
    private final AuthenticationFailureHandler customerAFHandler;
    private final LoginUserService loginUserService;

    public SecurityConfig(LogoutHandler customerLogoutHandler,
                          LogoutSuccessHandler customerLogoutSuccessHandler,
                          AuthenticationSuccessHandler customerASHandler,
                          AuthenticationFailureHandler customerAFHandler,
                          LoginUserService loginUserService) {
        super();
        this.customerLogoutHandler = customerLogoutHandler;
        this.customerLogoutSuccessHandler = customerLogoutSuccessHandler;
        this.customerASHandler = customerASHandler;
        this.customerAFHandler = customerAFHandler;
        this.loginUserService = loginUserService;
    }

    @Bean
    public SecurityFilterChain fromLoginFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/login").csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/login/failure").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .failureForwardUrl("/login/failure")
                .successForwardUrl("/login/success")
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customerLogoutHandler)
                .logoutSuccessHandler(customerLogoutSuccessHandler);
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiLoginFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/api/v1/login")
                .csrf().disable()
                .cors()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(customerASHandler)
                .failureHandler(customerAFHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customerLogoutHandler)
                .logoutSuccessHandler(customerLogoutSuccessHandler);
        return http.build();
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(loginUserService);
//        return daoAuthenticationProvider;
//    }
}
