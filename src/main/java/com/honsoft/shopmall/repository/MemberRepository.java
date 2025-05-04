package com.honsoft.shopmall.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.honsoft.shopmall.entity.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
 
	Member findByMemberId(String memberId);
}