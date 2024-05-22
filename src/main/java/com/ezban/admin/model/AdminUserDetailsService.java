package com.ezban.admin.model;

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
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            TypedQuery<Admin> query = entityManager.createQuery(
                "SELECT a FROM Admin a WHERE a.adminAccount = :username", Admin.class);
            query.setParameter("username", username);
            Admin admin = query.getSingleResult();

            return User.builder()
                .username(String.valueOf(admin.getAdminNo())) // Assuming you have adminNo in Admin class
                .password(admin.getAdminPwd())
                .roles("ADMIN")
                .build();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("帳號或密碼不正確");
        }
    }
}
