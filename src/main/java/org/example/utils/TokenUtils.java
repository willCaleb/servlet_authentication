package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.example.pattern.Constants;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class TokenUtils {


    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    public static String generateToken(String username, String userId) {

        Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);

        return JWT.create()
                .withIssuer("auth-provider")
                .withSubject(userId)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static DecodedJWT validarToken(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(Constants.TOKEN_SECRET);

        JWTVerifier verificador = JWT.require(algoritmo)
                .withIssuer("auth-provider")
                .build();

        return verificador.verify(token);
    }

    public static String getClaim(String token, String claim) {
        return validarToken(token).getClaim(claim).asString();
    }

    public static Date getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }

}
