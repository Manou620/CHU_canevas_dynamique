package com.chu.canevas.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Getter
    private Long id;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * @return 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @return 
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return 
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * @return 
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * @return 
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * @return 
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * @return 
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
