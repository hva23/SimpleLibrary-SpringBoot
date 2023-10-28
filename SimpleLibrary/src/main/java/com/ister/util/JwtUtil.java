package com.ister.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "hda#87dKipq(0qwe4";


    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    public Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return getUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /* Private Methods */
    private String createToken(Map<String, Object> claims, String subject) {
        final int mS_TO_HOUR_FACTOR = 1000 * 60 * 60;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + mS_TO_HOUR_FACTOR * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = getAllClaims(token);
        return claimsTFunction.apply(claims);
    }
}
