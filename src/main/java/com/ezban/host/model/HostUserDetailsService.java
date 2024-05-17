package com.ezban.host.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Service
public class HostUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            TypedQuery<Host> query = entityManager.createQuery(
                "SELECT h FROM Host h WHERE h.hostAccount = :username", Host.class);
            query.setParameter("username", username);
            Host host = query.getSingleResult();

            return User.builder()
                .username(String.valueOf(host.getHostNo()))
                .password(host.getHostPwd())
                .roles("HOST") 
                .build();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("帳號或密碼不正確");
        }
    }
}
