package com.ezban.member.model;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;


@Service
public class MemberAuthenticate {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseEntity<String> checkAccount(String memberMail, String memberPwd) {
        try {
            // 使用参数化查询来防止 SQL 注入
            int accountCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM member WHERE member_mail = ?",
                    Integer.class,
                    memberMail);

            if (accountCount == 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帐号或密码错误");
            }

            // 从数据库中获取存储的哈希密码
            String hashedPassword = jdbcTemplate.queryForObject(
                    "SELECT member_pwd FROM member WHERE member_mail = ?",
                    String.class,
                    memberMail);

            // 检查用户提供的密码与数据库中存储的哈希密码是否匹配
            if (!BCrypt.checkpw(memberPwd, hashedPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("帐号或密码错误");
            }

            return ResponseEntity.ok("登录成功");
        } catch (Exception e) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("内部服务器错误");
        }
    }

}
