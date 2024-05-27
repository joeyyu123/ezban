package com.ezban.member;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.member.model.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    private MemberService memService;
    
    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/currentLogin")
    @ResponseBody
    public Member getCurrentMember(Principal principal) {
        if (principal == null) {
            // 處理使用者未登入的情況
            System.out.println("目前沒有使用者登入。");
            return null; // 或者返回一個適當的錯誤響應
        }

        // 從 Principal 中獲取使用者名稱
        String username = principal.getName();
        System.out.println("User Name ================ " + username);

        // 使用使用者名稱查找 Member
        Optional<Member> member = memService.findMemberByMemberNo(username);
        return member.orElse(null); // 返回 JSON 數據
    }



    @PutMapping("/update/{memberMail}")
    @ResponseBody
    public String updateMember(@PathVariable String memberMail, @RequestBody Member member) {
        Optional<Member> existingMemberOptional = memberRepository.findByMemberMail(memberMail);
        if (existingMemberOptional.isPresent()) {
            Member existingMember = existingMemberOptional.get();
            existingMember.setMemberName(member.getMemberName());
            existingMember.setAddress(member.getAddress());
            existingMember.setMemberPhone(member.getMemberPhone());
            existingMember.setCommonRecipient(member.getCommonRecipient());
            existingMember.setCommonRecipientPhone(member.getCommonRecipientPhone());
            existingMember.setCommonRecipientAddress(member.getCommonRecipientAddress());
            memberRepository.save(existingMember);
            return "會員資料更新成功";
        } else {
            return "找不到相應的會員資料";
        }
    }
    
    @GetMapping("/memberCenterPage")
	public String getMemberCenterPage() {
		return "frontstage/member/memberCenter";
	}
}
