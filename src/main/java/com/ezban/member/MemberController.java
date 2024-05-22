package com.ezban.member;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService memservice;

	@GetMapping
	@ResponseBody
	public List<Member> getAllMembers() {

		return memservice.getAllMembers();
	}
	
	@GetMapping("/{memberNo}")
	@ResponseBody
	public Member getMemberById(@PathVariable Integer memberNo) {
		
	    return memservice.getMemberById(memberNo);
	}


	@GetMapping("/mail/{memberMail}")
	@ResponseBody
	public Member getMemberByMemberMail(@PathVariable String memberMail) {

		return memservice.getMemberByMemberMail(memberMail);
	}

	@GetMapping("/name/{memberName}")
	@ResponseBody
	public Member getMemberByMemberName(@PathVariable String memberName) {

		return memservice.getMemberByMemberName(memberName);
	}

	@GetMapping("/phone/{memberPhone}")
	@ResponseBody
	public Member getMemberByMemberPhone(@PathVariable String memberPhone) {

		return memservice.getMemberByMemberPhone(memberPhone);
	}
	
	@GetMapping("/getstatus/{memberStatus}")
	@ResponseBody
	public List<Member> getMembersByStatus(@PathVariable Byte memberStatus) {
		
		return memservice.getMembersByStatus(memberStatus);
	}

	@PutMapping("/status/{memberMail}")
	@ResponseBody
	public void updateMemberStatus(@RequestParam Byte memberStatus, @PathVariable String memberMail) {

		memservice.updateMemberStatus(memberStatus, memberMail);
	}

	@PutMapping("/points/{memberMail}")
	@ResponseBody
	public void updateMemberPoints(@RequestParam Integer memberPoints, @PathVariable String memberMail) {

		memservice.updateMemberPoints(memberPoints, memberMail);
	}
	
	@GetMapping("/memberPage")
	public String getMemberPage() {
		return "frontstage/member/member";
	}

}
