package com.chu.canevas.service.implementation;

import com.chu.canevas.dto.LoginRequest;
import com.chu.canevas.security.JwtTokenProvider;
import com.chu.canevas.security.UserDetailsImpl;
import com.chu.canevas.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * @param loginRequest
     * @return
     */
    @Override
    public Map<String, Object> authenticateUser(LoginRequest loginRequest) {

//        System.out.println("password de l'user : " + loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        if(authentication.isAuthenticated()){
            System.out.println("User authenticated");
        }

//        System.out.println("password de l'user : " + loginRequest.getPassword());

        // Set authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate JWT token
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Get user details from authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Prepare response with token and user details
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("username", userDetails.getUsername());
        response.put("role", userDetails.getAuthorities());

        return response;


    }
}
