package com.example.uberAuthService.helper;

import com.example.uberAuthService.model.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


// why we use it
// because spring security works on UserDetail polymorphic type for auth

public class AuthPassengerDetail extends Passenger implements UserDetails {

    private final String username;  // means email / name / id , unique data

    private final String password;

    public AuthPassengerDetail(Passenger passenger){
        this.username = passenger.getEmail();
        this.password = passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
