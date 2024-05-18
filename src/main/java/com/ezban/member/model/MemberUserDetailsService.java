package com.ezban.member.model;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		try {
            TypedQuery<Member> query = entityManager.createQuery(
                "SELECT m FROM Member m WHERE m.memberMail = :username", Member.class);
            query.setParameter("username", username);
            Member mem = query.getSingleResult();

            return User.builder()
                .username(String.valueOf(mem.getMemberNo()))
                .password(mem.getMemberPwd())
                .roles("Member") // 您可以根據需求設置適當的角色
                .build();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("帳號或密碼不正確");
        }
    }

}

