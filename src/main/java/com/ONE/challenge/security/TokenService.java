package com.ONE.challenge.security;

import com.ONE.challenge.modelo.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.Secret}")
    private String apiSecret;
    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("ONE")
                    .withSubject(usuario.getEmail())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(generarFechaExpiracion(4))
                    .sign(algorithm);

        } catch (JWTVerificationException exception){
            throw  new RuntimeException();
        }
    }

    public String  getSubject(String token){
        DecodedJWT verifier = null;
        if(token == null){
            throw new RuntimeException();
        } else {
            try {
                Algorithm algorithm = Algorithm.HMAC256(apiSecret);
                verifier = JWT.require(algorithm)
                        .withIssuer("ONE")
                        .build()
                        .verify(token);
                verifier.getSubject();
            }catch (JWTVerificationException exception){
                exception.printStackTrace();
            }
        }

        if(verifier == null || verifier.getSubject() == null){
            throw new RuntimeException("invalid verifier");
        }else {
            return verifier.getSubject();
        }
    }

    private Instant generarFechaExpiracion(Integer numHrs){
        return LocalDateTime.now().plusHours(numHrs).toInstant(ZoneOffset.of("-06:00"));
    }

}
