package com.sample.payconiq.stocks.utils;

import com.sample.payconiq.stocks.configuration.JWTPropertiesConfiguration;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.sample.payconiq.stocks.utils.Constants.*;

@RequiredArgsConstructor
@Component
public class JwtTokenUtil {


    private final JWTPropertiesConfiguration jwtPropertiesConfiguration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        if (roles.contains(new SimpleGrantedAuthority(ROLE_ADMIN))) {
            claims.put(IS_ADMIN, true);
        }
        if (roles.contains(new SimpleGrantedAuthority(ROLE_USER))) {
            claims.put(IS_USER, true);
        }

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtPropertiesConfiguration.getTokenValidity())))
                .signWith(SignatureAlgorithm.HS512, jwtPropertiesConfiguration.getSecretKey()).compact();

    }

    public boolean validateToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parser().setSigningKey(jwtPropertiesConfiguration.getSecretKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtPropertiesConfiguration.getSecretKey()).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtPropertiesConfiguration.getSecretKey()).parseClaimsJws(token).getBody();

        List<SimpleGrantedAuthority> roles = null;

        Boolean isAdmin = claims.get(IS_ADMIN, Boolean.class);
        Boolean isUser = claims.get(IS_USER, Boolean.class);

        if (isAdmin != null && isAdmin) {
            roles = Arrays.asList(new SimpleGrantedAuthority(ROLE_ADMIN));
        }

        if (isUser != null && isUser) {
            roles = Arrays.asList(new SimpleGrantedAuthority(ROLE_USER));
        }
        return roles;

    }


}
