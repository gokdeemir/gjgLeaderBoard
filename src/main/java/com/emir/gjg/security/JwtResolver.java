package com.emir.gjg.security;

import com.emir.gjg.enums.Role;
import com.emir.gjg.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static com.emir.gjg.constant.ErrorConstants.EXPIRED_TOKEN;
import static com.emir.gjg.constant.ErrorConstants.INVALID_TOKEN;

@Component
public class JwtResolver {

    @Value("${jwt.secret}")
    private String secret;

    public UUID getIdFromToken(String token) {
        tokenExpireCheck(token);
        String idString;
        try {
            idString = getClaimFromToken(token, Claims::getSubject);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
        return UUID.fromString(idString);
    }

    public Role getRoleFromToken(String token) {
        tokenExpireCheck(token);
        try {
            return Role.valueOf(getClaimFromToken(token, Claims::getAudience));
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    public String getMailFromToken(String token) {
        tokenExpireCheck(token);
        try {
            return getClaimFromToken(token, Claims::getIssuer);
        } catch (Exception e) {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private void tokenExpireCheck(String token) {
        try {
            if (new Date().after(getClaimFromToken(token, Claims::getExpiration))) {
                throw new UnauthorizedException(EXPIRED_TOKEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

}
