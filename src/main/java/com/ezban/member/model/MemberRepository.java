package com.ezban.member.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByMemberMail(String memberMail);

	Optional<Member> findByMemberName(String memberName);

	Optional<Member> findByMemberPhone(String memberPhone);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.memberStatus = ?1 WHERE m.memberMail = ?2")
	void updateMemberStatus(Byte memberStatus, String memberMail);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.memberPoints = ?1 WHERE m.memberMail = ?2")
	void updateMemberPoints(Integer memberPoints, String memberMail);

}
