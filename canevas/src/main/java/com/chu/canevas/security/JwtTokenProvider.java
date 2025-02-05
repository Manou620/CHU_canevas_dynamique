package com.chu.canevas.security;

import com.chu.canevas.exception.JwtTokenException;
import com.chu.canevas.exception.JwtTokenExpiredException;
import com.chu.canevas.exception.JwtTokenMalformedException;
import com.chu.canevas.exception.JwtTokenSignatureException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        String role = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");
        System.out.println(role);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts
                .builder()
                .claims().add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                //.expiration(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)))//apres 2 jours
                .and()
                .signWith(getKey())
                .compact();

    }

    public String getUsernameFromJwtToken(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){
            System.out.println(e.getMessage());
            throw new JwtTokenExpiredException();
        }catch (MalformedJwtException e){
            System.out.println(e.getMessage());
            throw new JwtTokenMalformedException();
        }catch (SignatureException e){
            System.out.println(e.getMessage());
            throw new JwtTokenSignatureException();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new JwtTokenException("La validation du token a echoue");
        }
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Username ExtractionProcess
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
