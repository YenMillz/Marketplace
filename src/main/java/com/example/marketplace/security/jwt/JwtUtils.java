package com.example.marketplace.security.jwt;

import com.example.marketplace.exception.InvalidTokenRequestException;
import com.example.marketplace.mapper.UserMapper;
import com.example.marketplace.model.User;
import com.example.marketplace.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${marketplace.app.jwtSecret}")
    private String jwtSecret;

    @Value("${marketplace.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    UserMapper userMapper;

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        /*return generateTokenFromUsername(userPrincipal.getUsername());*/
        return generateTokenFromId(userPrincipal.getId());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateTokenFromId(Long userId) {

        User user = userMapper.findById(userId);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuer(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getIdFromJwtToken(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject());
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getIssuer();
    }

    public Long getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getId();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            throw new InvalidTokenRequestException("Incorrect signature");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenRequestException("Malformed jwt token");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenRequestException("Token expired. Refresh required.");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenRequestException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenRequestException("Illegal argument token");
        }
    }
}
