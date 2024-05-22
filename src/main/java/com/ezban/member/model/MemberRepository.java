package com.ezban.member.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByMemberMail(String memberMail);

	// 生日優惠券排程用
	@Query("SELECT m FROM Member m WHERE FUNCTION('MONTH', m.birthday) = :month AND m.memberStatus = :status")
	List<Member> findMembersByBirthdayMonthAndStatus(int month, byte status);

}
