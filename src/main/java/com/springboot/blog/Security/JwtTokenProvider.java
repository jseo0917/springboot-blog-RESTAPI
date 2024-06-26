package com.springboot.blog.Security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exceptionHandler.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpriationDate;

    // generate JWT Token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpriationDate);
        
        String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(key())
            .compact();

        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Getting username from JWT
    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }

    // Validate JWT toekn
    public boolean validateToken(String token) {     
        try{   
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);            
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expried JWT token");
        }catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token.");
        }catch (IllegalArgumentException illegalArgumentException) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Illegal Argument exception.");
        }
        
    }
}
