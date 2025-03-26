package com.kurierfree.server.domain.auth.infra;

import com.kurierfree.server.domain.auth.domain.JwtToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.kurierfree.server.domain.auth.constant.JwtGrantType.GRANT_TYPE_USER;
import static com.kurierfree.server.domain.auth.constant.JwtTokenExpireTime.ACCESS_TOKEN_EXPIRE_TIME;
import static com.kurierfree.server.domain.auth.constant.JwtTokenExpireTime.REFRESH_TOKEN_EXPIRE_TIME;

@Component
public class JwtGenerator {

    private final Key key;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtGenerator(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Long userId, String grantType) {
        // 권한 가져오기

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME.getExpireTime());

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
//                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME.getExpireTime()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}