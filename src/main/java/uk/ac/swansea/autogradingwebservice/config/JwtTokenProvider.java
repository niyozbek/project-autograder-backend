package uk.ac.swansea.autogradingwebservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${app.jwtSecret:test}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMinutes:60}")
    private int jwtExpirationInMinutes;

    public String generateUserToken(Authentication authentication) {

        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        Date now = new Date();
        var expTimeInMs = jwtExpirationInMinutes * 1000 * 60;
        Date expiryDate = new Date(now.getTime() + expTimeInMs);
        return JWT.create()
                .withExpiresAt(expiryDate)
                .withSubject(user.getId().toString() + user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
    }

    public String getUserUsernameFromJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes()))
                .build();    //

        DecodedJWT jwt = verifier.verify(token);

        return jwt.getClaim("username").asString();
    }

    public boolean validateToken(String authToken) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes()))
                    .build();
            verifier.verify(authToken);
            return true;
        } catch (JWTDecodeException ex) {
            log.error("Invalid JWT token");
        } catch (TokenExpiredException ex) {
            log.error("Expired JWT token");
        } catch (JWTVerificationException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
