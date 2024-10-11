package com.chu.canevas.security;

import com.chu.canevas.exception.JwtTokenExpiredException;
import com.chu.canevas.exception.JwtTokenMalformedException;
import com.chu.canevas.exception.JwtTokenSignatureException;
import com.chu.canevas.model.Utilisateur;
import com.chu.canevas.service.implementation.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        try {
            if(token != null && jwtTokenProvider.validateToken(token)){
                String username = jwtTokenProvider.getUsernameFromJwtToken(token);
                System.out.println("Utilisateur from token : " + username);
                UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
                //UserDetails userDetails = user;
//                if(userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null ){ //s'assurer que l'utilisateur ne s'est pas encore authentifier
//                    var authentication = new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
                if(user != null && SecurityContextHolder.getContext().getAuthentication() == null ){ //s'assurer que l'utilisateur ne s'est pas encore authentifier
                    request.setAttribute("utilisateur_id", user.getId());
                    var authentication = new JwtAuthenticationToken(user, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }catch (JwtTokenExpiredException | JwtTokenMalformedException | JwtTokenSignatureException e) {
            SecurityContextHolder.clearContext();
            request.setAttribute("exception", e); // Attach exception to the request
            throw new BadCredentialsException(e.getMessage(), e); // Rethrow the specific exception
        }
//        catch (HttpMessageNotReadableException e){
//            SecurityContextHolder.clearContext();
//            request.setAttribute("exception", e);
//            throw new HttpMessageNotReadableException(e.getMessage(), e);
//        }
    }
}
