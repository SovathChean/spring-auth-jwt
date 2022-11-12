package com.sovathc.authjwt.common.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.sovathc.authjwt.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class JwtVerified {

    public static DecodedJWT getVerified(String token) throws BusinessException {


            Algorithm algorithm = JwtAlgorithm.encrptedAlgorithm();
            JWTVerifier verifier = JWT.require(algorithm).build();
//            decodedJWT = verifier.verify(token);

        return verifier.verify(token);
    }
    public DecodedJWT getBearerToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        String requestHeaderToken = request.getHeader(AUTHORIZATION);
        DecodedJWT decodedJWT = null;
        if(!request.getServletPath().equals("/api/login") && requestHeaderToken != null && requestHeaderToken.startsWith("Bearer"))
        {
            String token = requestHeaderToken.substring("Bearer ".length());
            decodedJWT = getVerified(token);
        }
        return decodedJWT;
    }

}
