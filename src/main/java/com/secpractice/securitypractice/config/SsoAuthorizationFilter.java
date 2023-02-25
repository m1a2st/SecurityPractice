package com.secpractice.securitypractice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secpractice.securitypractice.exception.SsoException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author
 * @Date
 * @Version
 * @Description
 */
public class SsoAuthorizationFilter extends OncePerRequestFilter {

    private final RequestMatcher requestMatcher;
    private final String SSO_URL = "https://127.0.0.1:8888/sso/login";
    private final RestTemplate restTemplate;

    public SsoAuthorizationFilter(String loginProcessUrl, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        Assert.notNull(loginProcessUrl, "loginUrl can't be null");
        this.requestMatcher = new AntPathRequestMatcher(loginProcessUrl, "POST");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 攔截登入
        if (requestMatcher.matches(request)) {
            String userName = getUserName(request);
            String ssoTime = getSSOTime(request);
            String ssoDate = getSSODate(request);
            String md5 = getMd5(request);

            if (!checkMd5(userName, ssoTime, ssoDate, md5)) {
                throw new SsoException("md5 is wrong");
            }

            URI uri = UriComponentsBuilder.fromHttpUrl(SSO_URL)
                    .queryParam(newMd5(userName, ssoTime, ssoDate))
                    .build().toUri();
            ResponseEntity<Objects> res = restTemplate.getForEntity(uri, Objects.class);

            response.getWriter().print(new ObjectMapper().writeValueAsString(res));
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String newMd5(String userName, String ssoTime, String ssoDate) {
        return "";
    }

    private boolean checkMd5(String userName, String ssoTime, String ssoDate, String md5) {
        return "".equals(userName);
    }

    private String getMd5(HttpServletRequest request) {
        return Optional.of(request.getParameter("md5"))
                .orElseThrow(() -> new SsoException("This user name is empty"));
    }

    private String getSSODate(HttpServletRequest request) {
        return Optional.of(request.getParameter("SSODate"))
                .orElseThrow(() -> new SsoException("This user name is empty"));
    }

    private String getSSOTime(HttpServletRequest request) {
        return Optional.of(request.getParameter("SSOTime"))
                .orElseThrow(() -> new SsoException("This user name is empty"));
    }

    private String getUserName(HttpServletRequest request) {
        return Optional.of(request.getParameter("userName"))
                .orElseThrow(() -> new SsoException("This user name is empty"));
    }

}
