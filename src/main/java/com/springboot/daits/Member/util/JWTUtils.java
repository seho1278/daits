package com.springboot.daits.Member.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import io.jsonwebtoken.Jwts;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;


@UtilityClass
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secretKey;
    private static final Long expired = 1000L * 60 * 60;

    public static String getIssuer(String token) {

        String issuer = JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
                .build()
                .verify(token)
                .getIssuer();

        return issuer;
    }

    // 토큰 생성
    public String createJwt (Long id, String email, String userName) {

        Claim claim = (Claim) Jwts.claims().setSubject(email);

        return JWT.create()
//                .withExpiresAt(expired)
                .withClaim("user_id", id)
                .withSubject(userName)
                .withIssuer(email)
                .sign(Algorithm.HMAC512(secretKey.getBytes()));

    }

}
