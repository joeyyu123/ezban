package com.ezban.member.model;

import com.ezban.host.model.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Integer memberNo) {
        return memberRepository.findById(memberNo).orElse(null);
    }

    public Optional<Member> findMemberByEmail(String memberMail) {
        return memberRepository.findByMemberMail(memberMail);
    }

    public Member getMemberByMemberNo(String memberNo) {
        try {
            Optional<Member> member = memberRepository.findById(Integer.valueOf(memberNo));
            return member.orElse(null);
        } catch (NumberFormatException e) {
            // Log the error message
            e.printStackTrace();
            return null;
        }
    }

    public void updateMember(String memberMail, Member member) {
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
        }
    }

    public Member getMemberByMemberMail(String memberMail) {
        Optional<Member> optionalMember = memberRepository.findByMemberMail(memberMail);
        return optionalMember.orElse(null);
    }

    public void updateMemberPoints(Integer memberPoints, String memberMail) {
        memberRepository.updateMemberPoints(memberPoints, memberMail);
    }

    // 聊天室用, 透過 memberNo 取得 memberName
    public Member findMemberByIdChat(Integer memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(() -> new RuntimeException("Member not found"));
    }

	public String getMemberNameByMemberNoChat(Integer memberNo) {
		Member member = findMemberByIdChat(memberNo);
		return member.getMemberName();
	}

	public Optional<Member> findMemberByMemberNo(String memberNo) {
		return memberRepository.findMemberByMemberNo(Integer.valueOf(memberNo));
	}
}
