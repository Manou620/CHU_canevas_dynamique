package com.chu.canevas.service;

import com.chu.canevas.dto.LoginRequest;

import java.util.Map;

public interface AuthenticationService {

    public Map<String, Object> authenticateUser(LoginRequest loginRequest);

}
