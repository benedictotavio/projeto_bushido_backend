package br.org.institutobushido.services.admin.infra;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.org.institutobushido.model.admin.Admin;

@Service
public class TokenServices {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Admin admin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.create()
                    .withIssuer(secret)
                    .withClaim("email", admin.getEmail())
                    .withSubject(admin.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error ao gerar token", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.require(algorithm).withIssuer(secret).build().verify(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(token, e);
        }
    }
}