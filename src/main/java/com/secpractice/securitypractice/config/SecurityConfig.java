package com.secpractice.securitypractice.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import static com.secpractice.securitypractice.utils.ResponseUtils.responseJsonWriter;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
@Log4j2
@EnableWebSecurity
public class SecurityConfig{

    private final AuthenticationSuccessHandler customerASHandler;
    private final AuthenticationFailureHandler customerAFHandler;

    public SecurityConfig(AuthenticationFailureHandler customerAFHandler, AuthenticationSuccessHandler customerASHandler) {
        super();
        this.customerAFHandler = customerAFHandler;
        this.customerASHandler = customerASHandler;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/login","/login/failure").permitAll()
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
                .addLogoutHandler(customerLogoutHandler())
                .logoutSuccessHandler(customerLogoutSuccessHandler());
        return http.build();
    }

    public LogoutHandler customerLogoutHandler(){
        return (request, response, authentication) -> {
            log.info("logout handler is running....");
        };
    }
    @Bean
    public LogoutSuccessHandler customerLogoutSuccessHandler () {
        return (request, response, authentication) -> responseJsonWriter(response, authentication.getName());
    }


}
