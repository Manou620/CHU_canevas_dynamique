package com.chu.canevas.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;

    public JwtAuthenticationToken(UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); //Mark this as authenticated
    }

    /**
     * @return 
     */
    @Override
    public Object getCredentials() {
        return null;// JWT authentication doesn’t require credentials like passwords
    }

    /**
     * @return 
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
