package com.ezban.member.model;

import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;

@Service
public class MemebrAuthenticate {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public ResponseEntity<String> checkAccount(String memberMail, String memberPwd) {
		
		try {
			int accountCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member WHERE member_mail = ?", Integer.class, memberMail);
			if(accountCount == 0) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
			}
			
			int matchCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM member WHERE member_mail = ? AND member_pwd = ?", Integer.class, memberMail, memberPwd);
			
			if(matchCount == 0) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帳號或密碼錯誤");
			}
			
			return ResponseEntity.ok("登入成功");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("發生內部錯誤:" + e.getMessage());
		}
	}

}
