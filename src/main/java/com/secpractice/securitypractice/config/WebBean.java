package com.secpractice.securitypractice.config;

import com.secpractice.securitypractice.utils.JwtTokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.secpractice.securitypractice.utils.ResponseUtils.responseJsonWriter;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
@Log4j2
public class WebBean {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("userPass"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("adminPass"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }

    @Bean
    public AuthenticationSuccessHandler customerASHandler() {
        return (request, response, authentication) -> {
            if (response.isCommitted()) {
                log.debug("Response is already commit");
            }
            Map<String, String> map = new HashMap<>();
            map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            map.put("msg", "success_login");
            map.put("name", authentication.getName());
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            Set<String> role = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            String token = JwtTokenUtils.generateToken(map);
            responseJsonWriter(response, token);
        };
    }

    @Bean
    public AuthenticationFailureHandler customerAfHandler() {
        return (request, response, exception) -> responseJsonWriter(response,"login fail");
    }
}
