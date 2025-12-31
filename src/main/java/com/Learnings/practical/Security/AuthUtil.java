package com.Learnings.practical.Security;

import com.Learnings.practical.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

//create json web token
@Component
public class AuthUtil {

   @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public static String getUsernameFromToken(String token) {

     Claims claims =  Jwts.parser()
             .verifyWith(getSecretKey())
             .build()
             .parseSignedClaims(token)
             .getPayload();

   return claims.getSubject();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

   public String generateToken(User user) {
     return  Jwts.builder()
               .subject(user.getUsername())
//               .claim("email", user.getEmail())
//               .claim("roles", Set.of("ADMIN", "USER"))
               .claim("UserId",user.getId().toString())
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() + 3000 * 1000))
               .signWith(getSecretKey())
               .compact();

   }

   public long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())//we are created builder for  the secret key hten passed
                // it forward  then we got payload

                .build()
                .parseSignedClaims(token)
                .getPayload() ;
        return Long.parseLong(claims.getSubject());
   }
}
