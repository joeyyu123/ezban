package com.ezban.host.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class HostUserDetails implements UserDetails {

    private final Host host;

    public HostUserDetails(Host host) {
        this.host = host;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_HOST");
    }

    @Override
    public String getPassword() {
        return host.getHostPwd();
    }

    @Override
    public String getUsername() {
        return host.getHostAccount();
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
        return host.getHostStatus() == 1;
    }
}
