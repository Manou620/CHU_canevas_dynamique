package com.chu.canevas.controller;

import com.chu.canevas.dto.LoginRequest;
import com.chu.canevas.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authentification(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
    }

}
