package com.secpractice.securitypractice.config;

import com.secpractice.securitypractice.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
@Configuration
public class SecurityExceptionBean {

    @Bean
    public AccessDeniedHandler customerADHandler() {
        return (request, response, accessDeniedException) -> {
            Map<String, String> map = new HashMap<>() {{
                put("uri", request.getRequestURI());
                put("msg", "access denied!");
            }};
            ResponseUtils.responseJsonWriter(response, map);
        };
    }

    @Bean
    public AuthenticationEntryPoint customerAEPoint() {
        return (request, response, authException) -> {
            Map<String, String> map = new HashMap<>() {{
                put("uri", request.getRequestURI());
                put("msg", "authentication fail!");
            }};
            ResponseUtils.responseJsonWriter(response, map);
        };
    }
}
