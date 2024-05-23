package com.ezban.member.model;

import com.ezban.host.model.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memrepository;

	public List<Member> getAllMembers() {
		return memrepository.findAll();
	}

	public Member getMemberById(Integer memberNo) {
		return memrepository.getById(memberNo);
	}
	public Member getMemberByMemberNo(String memberNo) {
		return memrepository.getReferenceById(Integer.valueOf(memberNo));
	}

	public Member getMemberByMemberMail(String memberMail) {
		Optional<Member> optionalMember = memrepository.findByMemberMail(memberMail);
		return optionalMember.orElse(null);
	}

	public Member getMemberByMemberName(String memberName) {
		Optional<Member> optionalMember = memrepository.findByMemberName(memberName);
		return optionalMember.orElse(null);
	}

	public Member getMemberByMemberPhone(String memberPhone) {
		Optional<Member> optionalMember = memrepository.findByMemberPhone(memberPhone);
		return optionalMember.orElse(null);
	}
	
	public List<Member> getMembersByStatus(Byte memberStatus) {
        return memrepository.findByMemberStatus(memberStatus);
    }

	public void updateMemberStatus(Byte memberStatus, String memberMail) {
		memrepository.updateMemberStatus(memberStatus, memberMail);
	}

	public void updateMemberPoints(Integer memberPoints, String memberMail) {
		memrepository.updateMemberPoints(memberPoints, memberMail);
	}

	// 聊天室用,透過memberNo取得memberName
	public Member findMemberByIdChat(Integer memberNo) {
		return memrepository.findById(memberNo)
				.orElseThrow(() -> new RuntimeException("Member not found"));
	}

	public String getMemberNameByMemberNoChat(Integer memberNo) {
		Member member = findMemberByIdChat(memberNo);
		return member.getMemberName();
	}

	public Optional<Member> findMemberByMemberNo(String memberNo) {
		return memrepository.findMemberByMemberNo(Integer.valueOf(memberNo));
	}
}
