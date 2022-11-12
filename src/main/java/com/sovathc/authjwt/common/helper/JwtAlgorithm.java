package com.sovathc.authjwt.common.helper;


import com.auth0.jwt.algorithms.Algorithm;

public class JwtAlgorithm {

    public static Algorithm encrptedAlgorithm()
    {
        String SECRET_KEY = "secret";
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

        return algorithm;
    }

}
