package com.ezban.host.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;

@RestController
public class RegisterController {

	@Autowired
	MemberRepository memrepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
//	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)	
	@PostMapping("/registerMember")
	public ResponseEntity<String> registerMember(@RequestBody Member member) {
		try {
			Optional<Member> existingMember = memrepository.findByMemberMail(member.getMemberMail());
			if (existingMember.isPresent()) {
			    return ResponseEntity.status(HttpStatus.CONFLICT).body("該電子郵件已被使用");
			}

			memrepository.save(member);

			return ResponseEntity.ok("註冊成功");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊時發生內部錯誤" + e.getMessage());
		}
	}
}