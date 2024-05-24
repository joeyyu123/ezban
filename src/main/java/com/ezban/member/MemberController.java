package com.ezban.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    private MemberService memService;

    @GetMapping("/currentLogin")
    public Member getCurrentMember(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Member> member = memService.findMemberByEmail(userDetails.getUsername());
        return member.orElse(null); // 返回 JSON 数据
    }

    @PutMapping("/update/{memberMail}")
    public String updateMember(@PathVariable String memberMail, @RequestBody Member member) {
        memService.updateMember(memberMail, member);
        return "會員資料更新成功";
    }
    
    @GetMapping("/memberCenterPage")
	public String getRegister() {
		return "frontstage/member/memberCenter";
	}
}
