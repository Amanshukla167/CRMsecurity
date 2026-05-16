package com.aman.crmsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServices {

    private String ScerateKey = "sdfghj5678j#hgfd45678$%dfvgb87654rfvgh34567@";

    private Key getKey(){
        byte [] bytes = ScerateKey.getBytes();
        return Keys.hmacShaKeyFor(bytes);
    }

    private long Experication = 1000*60*15;


    public  String genrateToken(String email, String role){


        return Jwts.builder()
                .setSubject(email)
                .claim("role" , role)
                .setIssuedAt(new Date())
                .signWith(getKey() , SignatureAlgorithm.HS256)
                .setExpiration(new Date((System.currentTimeMillis() + Experication)))
                .compact();
    };


    private Claims extractTocken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public String extractEmail(String token){

      return extractTocken(token).getSubject();

    }
    public  String extractRole(String token){
        return  extractTocken(token).get("role" , String.class);
    }

    public Date extractExpirydate(String token){
        return  extractTocken(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        return  extractTocken(token).getExpiration().before(new Date());
    }

    public boolean isTokenValid (String email , String token){
      String emailformToken = extractEmail(token);

      return (email.equals(emailformToken) && !isTokenExpired(token));



    }
}
