package com.sovathc.authjwt.common.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sovathc.authjwt.authentication.biz.dto.OAuthTokenFormatDTO;

import java.util.Date;

public class JwtCreateToken {

    public static OAuthTokenFormatDTO createTokens(String issuer, String username, String uniqueKey)
    {
        final Integer refreshTokenLife = 10*60*24*60*1000;//60days
        final Integer accessTokenLife = 10*60*1000; //10min

        Algorithm algorithm = new JwtAlgorithm().encrptedAlgorithm();
        String access_token = JWT.create()
                .withSubject(username)
                .withClaim("tokenKey", uniqueKey)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenLife)) //10min
                .withIssuer(issuer)
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(username)
                .withClaim("tokenKey", uniqueKey)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenLife)) //60days
                .withIssuer(issuer)
                .sign(algorithm);

        OAuthTokenFormatDTO oAuthTokenFormatDTO = new OAuthTokenFormatDTO();
        oAuthTokenFormatDTO.setAccessToken(access_token);
        oAuthTokenFormatDTO.setRefreshToken(refresh_token);

        return oAuthTokenFormatDTO;
    }
}
