package com.chu.canevas.security;

import com.chu.canevas.exception.ElementNotFoundException;
import com.chu.canevas.exception.JwtTokenExpiredException;
import com.chu.canevas.exception.JwtTokenMalformedException;
import com.chu.canevas.exception.JwtTokenSignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        Object exception = request.getAttribute("exception");
        log.error("Exception : {}", String.valueOf(exception));
        System.out.print(exception);
        response.setContentType("application/json");

//        if(authException instanceof JwtTokenExpiredException){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("{\"error\": \"Token has expired\", \"status\": 401}");
//        }

        switch (exception) {
            case JwtTokenExpiredException jwtTokenExpiredException -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token has expiredddd\", \"status\": 401}");
            }
            case JwtTokenMalformedException jwtTokenMalformedException -> {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Token Invalide\", \"status\": 401}");
            }
            case JwtTokenSignatureException jwtTokenSignatureException -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"JWT signature validation failed\", \"status\": 401}");
            }
            case ElementNotFoundException elementNotFoundException -> {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Utilisateur introuvable\", \"status\": 404}");
            }
//            case HttpMessageNotReadableException httpMessageNotReadableException -> {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\": \"Requette illisible\", \"status\": 401}");
//            }
            case null, default -> {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Unauthorized accesssss\", \"status\": 401}");
            }
        }
    }
}
