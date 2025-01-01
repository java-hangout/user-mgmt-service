package com.jh.tds.ums.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    //    @Value("${jwt.secret}")
    private String SECRET_KEY = "sh2+3JRuzIaVMCGxBPeDMSzUFwDBLscv4R77LYntGns=";
    private Key secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
    // Secure key for HS256 algorithm using JJWT's Keys utility (recommended approach)
//    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generates a secure key for HS256
//private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private long expirationTime = 1000 * 60 * 60; // 1 hour in milliseconds

    // Generate a JWT token with the given username
    public String generateToken(String username, List<SimpleGrantedAuthority> authorities) {
        System.out.println("generateToken --->>> " + username);
//        System.out.println("SECRET_KEY --->>> " + SECRET_KEY);
        System.out.println("secretKey --->>> " + secretKey);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(claims)
//                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)  // Use the generated secure key to sign the token
                .compact();
    }



    // Extract the username (subject) from the JWT token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject(); // The subject is typically the username
    }

    /*public String[] extractRoles(String token) {
        Claims claims = extractClaims(token);
        List<String> roles = (List<String>) claims.get("roles");
        return roles.toArray(new String[0]);
    }*/
    public List<SimpleGrantedAuthority> extractRoles(String token) {
        Claims claims = extractClaims(token);
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    // Check if the token is expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());  // Compare token expiration with current time
    }

    /*// Validate the JWT token by comparing username and expiration status
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));  // Ensure the username matches and token isn't expired
    }*/
    public List<SimpleGrantedAuthority> getAuthoritiesFromRoles(String[] roles) {
        return Arrays.stream(roles)
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    /*public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }*/

    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Extract claims (payload) from the JWT token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()  // Use parserBuilder() as per newer JJWT versions
                .setSigningKey(secretKey)  // Set the same key to validate the token
                .build()
                .parseClaimsJws(token)
                .getBody();  // Extract and return claims from the JWT
    }
}
