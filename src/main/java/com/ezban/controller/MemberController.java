package com.ezban.controller;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private MemberService memservice;

	@GetMapping
	public List<Member> getAllMembers() {

		return memservice.getAllMembers();
	}
	
	@GetMapping("/{memberNo}")
	public Member getMemberById(@PathVariable Integer memberNo) {
	    return memservice.getMemberById(memberNo);
	}


	@GetMapping("/mail/{memberMail}")
	public Member getMemberByMemberMail(@PathVariable String memberMail) {

		return memservice.getMemberByMemberMail(memberMail);
	}

	@GetMapping("/name/{memberName}")
	public Member getMemberByMemberName(@PathVariable String memberName) {

		return memservice.getMemberByMemberName(memberName);
	}

	@GetMapping("/phone/{memberPhone}")
	public Member getMemberByMemberPhone(@PathVariable String memberPhone) {

		return memservice.getMemberByMemberPhone(memberPhone);
	}
	
	@GetMapping("/getstatus/{memberStatus}")
	public List<Member> getMembersByStatus(@PathVariable Byte memberStatus) {
		
		return memservice.getMembersByStatus(memberStatus);
	}

	@PutMapping("/status/{memberMail}")
	public void updateMemberStatus(@RequestParam Byte memberStatus, @PathVariable String memberMail) {

		memservice.updateMemberStatus(memberStatus, memberMail);
	}

	@PutMapping("/points/{memberMail}")
	public void updateMemberPoints(@RequestParam Integer memberPoints, @PathVariable String memberMail) {

		memservice.updateMemberPoints(memberPoints, memberMail);
	}

}
