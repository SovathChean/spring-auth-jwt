package com.sovathc.authjwt.common.config;

import com.sovathc.authjwt.authentication.biz.dao.OAuthTokenDAO;
import com.sovathc.authjwt.authentication.biz.dto.OAuthTokenFormatDTO;
import com.sovathc.authjwt.authentication.biz.service.AuthenticationService;
import com.sovathc.authjwt.common.helper.JwtCreateToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtUtilFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    public JwtUtilFilter(AuthenticationManager authenticationManager, AuthenticationService authenticationService)
    {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user= (User) authResult.getPrincipal();
        String uniqueKey = UUID.randomUUID().toString();
        String issuer  = request.getRequestURI().toString();

        OAuthTokenFormatDTO tokens = JwtCreateToken.createTokens(issuer, user.getUsername(), uniqueKey);
        Map<String, String> responseToken = new HashMap<>();
        responseToken.put("access_token", tokens.getAccessToken());
        responseToken.put("refresh_token", tokens.getRefreshToken());
        response.setContentType(APPLICATION_JSON_VALUE);
        authenticationService.storeUnqiueKey(uniqueKey);

        new ObjectMapper().writeValue(response.getOutputStream(), responseToken);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username {}:", username);
        log.info("password {}:", password);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }


}
