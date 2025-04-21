package com.toonpick.toonpick_backend.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    /** 기본 시크릿 키, application.properties 에서 덮어쓰기 가능 */
    @Value("${jwt.secret:S3Cretke3y}")
    private String secretKey;

    /** 기본 토큰 유효 기간 3600초(1시간) */
    @Value("${jwt.token-validity-in-seconds:3600}")
    private long tokenValidityInSeconds;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        // Base64 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /** 이메일을 subject로 JWT 생성 */
    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenValidityInSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
    }

    /** 토큰에서 이메일(subject) 꺼내기; 실패 시 예외만 출력하고 null 반환 */
    public String getUsername(String token) {
        try {
            Jws<Claims> parsed = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return parsed.getBody().getSubject();
        } catch (Exception e) {
            e.printStackTrace();  // 검증 실패 로그만 출력
            return null;
        }
    }

    /** 인증 정보 조회 */
    public Authentication getAuthentication(String token) {
        String email = getUsername(token);
        if (email == null) {
            return null;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
        );
    }

    /** 토큰 유효성만 체크 (만료 여부) */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 검증 실패 시 false 반환
            System.err.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }
}
