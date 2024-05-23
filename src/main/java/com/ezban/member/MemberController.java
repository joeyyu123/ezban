package com.ezban.member;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memservice;
    
    @GetMapping("/currentLogin")
    @ResponseBody
    public ResponseEntity<Member> getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberMail = authentication.getName(); // 获取当前用户的邮件（或用户名）

        Member member = memservice.getMemberByMemberMail(memberMail);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{memberNo}")
    @ResponseBody
    public ResponseEntity<Member> getMemberByMemberNo(@PathVariable String memberNo) {
        Member member = memservice.getMemberByMemberNo(memberNo);
        if (member != null) {
            return new ResponseEntity<>(member, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{memberMail}")
    @ResponseBody
    public ResponseEntity<String> updateMember(@PathVariable String memberMail, @RequestBody Member newMember) {
        Member existingMember = memservice.getMemberByMemberMail(memberMail);
        if (existingMember != null) {
            existingMember.setMemberName(newMember.getMemberName());
            existingMember.setAddress(newMember.getAddress());
            existingMember.setMemberPhone(newMember.getMemberPhone());
            existingMember.setCommonRecipient(newMember.getCommonRecipient());
            existingMember.setCommonRecipientPhone(newMember.getCommonRecipientPhone());
            existingMember.setCommonRecipientAddress(newMember.getCommonRecipientAddress());

            memservice.updateMember(existingMember);
            return new ResponseEntity<>("資料已更新", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("會員不存在", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("memberPage")
	public String getMemberPage() {
		return "frontstage/member/member";
	}
}
