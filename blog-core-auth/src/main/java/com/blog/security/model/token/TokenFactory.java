package com.blog.security.model.token;

import com.blog.security.model.Scopes;
import com.blog.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Token创建工厂类 {@link Token}.
 *
 * @author Levin
 * @since 2017-05-25
 */
@Component
public class TokenFactory {


    /**
     * 利用JJWT 生成 Token
     *
     * @param context
     * @return
     */
    public AccessToken createAccessToken(UserContext context) {
        Optional.ofNullable(context.getUserName()).orElseThrow(() -> new IllegalArgumentException("Cannot create " +
                "Token without username"));
        Optional.ofNullable(context.getAuthorities()).orElseThrow(() -> new IllegalArgumentException("User doesn't have any privileges"));
        Claims claims = Jwts.claims().setSubject(context.getUserName());
        claims.put("scopes", context.getAuthorities().stream().map(Object::toString).collect(toList()));
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://blog.battcn.com")
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(30)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, "battcn")
                .compact();
        return new AccessToken(token, claims);
    }

    /**
     * 生成 刷新 RefreshToken
     *
     * @param userContext
     * @return
     */
    public Token createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUserName())) {
            throw new IllegalArgumentException("Cannot create Token without username");
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Claims claims = Jwts.claims().setSubject(userContext.getUserName());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://blog.battcn.com")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(30)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, "battcn")
                .compact();

        return new AccessToken(token, claims);
    }
}
