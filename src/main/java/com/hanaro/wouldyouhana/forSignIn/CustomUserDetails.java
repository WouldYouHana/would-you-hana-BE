package com.hanaro.wouldyouhana.forSignIn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private String userEmail;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String userEmail) {
        super(username, password, authorities);
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}