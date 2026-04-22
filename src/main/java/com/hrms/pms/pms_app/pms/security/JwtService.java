package com.hrms.pms.pms_app.pms.security;

import com.hrms.pms.pms_app.pms.entities.Role;
import com.hrms.pms.pms_app.pms.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Getter
@Setter
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long accessTtlSeconds,
                      @Value("${jwt.refresh-expiration}") long refreshTtlSeconds,
                      @Value("${jwt.issuer}") String issuer) {
//        this.key = key;
        if(secret == null || secret.length() < 64){
            throw new IllegalArgumentException("Invalid Secret");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    //Generate Access Token
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        List<String> roles = user.getRoles() == null ? List.of() :
                user.getRoles().stream().map(Role::getName).toList();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "emp_id", user.getEmployee().getEmpId(),
                        "roles", roles,
                        "typ", "access"
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //Generate Refresh Token
    public String generateRefreshToken(User user, String jti) {
        Instant now = Instant.now();

        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds))) // longer TTL
                .claim(
                        "typ", "refresh"
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    //parse the token
    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(key) // same key used to sign
                .build()
                .parseSignedClaims(token);

    }

    public boolean isAccessToken(String token){
        Claims c = parse(token).getPayload();
        return "access".equals(c.get("typ"));
    }

    public boolean isRefreshToken(String token){
        Claims c = parse(token).getPayload();
        return "refresh".equals(c.get("typ"));
    }

    public UUID getUserId(String token){
        Claims c = parse(token).getPayload();
        return UUID.fromString(c.getSubject());
    }

    public String getJti(String token){
        return parse(token).getPayload().getId();

    }

    public List<String> getRoles(String token){
        Claims c = parse(token).getPayload();
        return (List<String>) c.get("roles");
    }

    public String gotEmail(String token){
        Claims c = parse(token).getPayload();
        return (String) c.get("email");
    }




}

