package com.sovathc.authjwt.common.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sovathc.authjwt.authentication.biz.service.AuthenticationService;
import com.sovathc.authjwt.common.exception.BusinessException;
import com.sovathc.authjwt.common.exception.RaiseException;
import com.sovathc.authjwt.common.helper.JwtAlgorithm;
import com.sovathc.authjwt.common.type.SysHttpResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;
    public AuthorizationFilter(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = StringUtils.isNotEmpty(request.getHeader(AUTHORIZATION)) ? request.getHeader(AUTHORIZATION) : null;

         if(request.getServletPath().equals("/api/login"))
             filterChain.doFilter(request, response);
         else
         {
             if(ObjectUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith("Bearer"))
             {
                 String token = requestTokenHeader.substring("Bearer ".length());

                 DecodedJWT decodedJWT = null;
                 try {
                     Algorithm algorithm = JwtAlgorithm.encrptedAlgorithm();
                     JWTVerifier verifier = JWT.require(algorithm).build();
                     decodedJWT = verifier.verify(token);
                 } catch (Exception e) {
                     RaiseException.exception(response, SysHttpResultCode.ERROR_401.getCode(), "Token is invalid or expire");
                     throw new BusinessException(SysHttpResultCode.ERROR_401.getCode(), "Token is invalid or expire");
                 }
                 //InvalidJwtToken
                 if(decodedJWT == null)
                 {
                     log.info("Jwt is invalid or expire.");
                     RaiseException.exception(response, SysHttpResultCode.ERROR_401.getCode(), "Token is invalid or expire");
                 }
                 assert decodedJWT != null;
                 String username = decodedJWT.getSubject();
                 String uniqueKey = decodedJWT.getClaim("tokenKey").asString();
                 if(!authenticationService.hasUniqueKey(uniqueKey))
                     RaiseException.exception(response, SysHttpResultCode.ERROR_401.getCode(), SysHttpResultCode.ERROR_401.getDescription());
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                         UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 filterChain.doFilter(request, response);
                 log.info("User is successfully login.");
             }
             else
             {
                 RaiseException.exception(response, SysHttpResultCode.ERROR_400.getCode(), "Authorization token is require.");
             }
         }
    }
}
