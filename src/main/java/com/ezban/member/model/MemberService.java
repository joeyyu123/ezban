package com.ezban.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memrepository;

    public Member getMemberByMemberNo(String memberNo) {
        try {
            Optional<Member> member = memrepository.findById(Integer.valueOf(memberNo));
            return member.orElse(null);
        } catch (NumberFormatException e) {
            // Log the error message
            e.printStackTrace();
            return null;
        }
    }

    public Member updateMember(Member member) {
        return memrepository.save(member);
    }

    public Member getMemberByMemberMail(String memberMail) {
        Optional<Member> optionalMember = memrepository.findByMemberMail(memberMail);
        return optionalMember.orElse(null);
    }
}
