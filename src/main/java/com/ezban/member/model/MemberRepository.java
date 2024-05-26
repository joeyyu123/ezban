package com.ezban.member.model;

import java.util.List;
import java.util.Optional;

import com.ezban.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByMemberMail(String memberMail);

	Optional<Member> findByMemberName(String memberName);

	Optional<Member> findByMemberPhone(String memberPhone);

	List<Member> findByMemberStatus(Byte memberStatus);

	Optional<Member> findMemberByMemberNo(Integer memberNo);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.memberStatus = ?1 WHERE m.memberMail = ?2")
	void updateMemberStatus(Byte memberStatus, String memberMail);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.memberPoints = ?1 WHERE m.memberMail = ?2")
	void updateMemberPoints(Integer memberPoints, String memberMail);

	// 生日優惠券排程用
	@Query("SELECT m FROM Member m WHERE FUNCTION('MONTH', m.birthday) = :month AND m.memberStatus = :status")
	List<Member> findMembersByBirthdayMonthAndStatus(int month, byte status);

	
}
