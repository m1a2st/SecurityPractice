package com.secpractice.securitypractice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    public SecurityConfig() {
        super();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/process")
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure")
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
        return new LogoutHandler() {
            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

            }
        };
    }
    @Bean
    public LogoutSuccessHandler customerLogoutSuccessHandler () {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            }

            private static void responseJsonWriter(HttpServletResponse response,String msg) throws IOException {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setCharacterEncoding("utf-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ObjectMapper objectMapper = new ObjectMapper();
                String resBody = objectMapper.writeValueAsString(msg);
                PrintWriter printWriter = response.getWriter();
                printWriter.print(resBody);
                printWriter.flush();
                printWriter.close();
            }
        };
    }


}
