package com.ezban.member.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberUserDetails implements UserDetails{
	
	private final Member mem;
	
	public MemberUserDetails(Member mem) {
		this.mem = mem;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singletonList(() -> "ROLE_Member");
	}
	
	@Override
	public String getPassword() {
		return mem.getMemberPwd();
	}
	
	@Override
	public String getUsername() {
		return mem.getMemberMail();
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
        return mem.getMemberStatus() == 1;
    }

}

