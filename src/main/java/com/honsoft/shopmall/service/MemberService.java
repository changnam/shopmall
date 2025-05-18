package com.honsoft.shopmall.service;


import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Primary
public class MemberService implements UserDetailsService {


    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public Member getMemberById(String memberId){
    	Member member = memberRepository.findByMemberId(memberId);
    	 System.out.println("22222   "+ member.getMemberId());
        return member;
    }
   
    public void deleteMember(String memberId){
    	Member member = memberRepository.findByMemberId(memberId);
    	memberRepository.deleteById(member.getNum());
    	 
        
    }    
  
     private void validateDuplicateMember(Member member){
    	 Member findMember = memberRepository.findByMemberId(member.getMemberId());
    	 if(findMember != null){
    		 throw new IllegalStateException("이미 가입된 회원입니다.");
    	}
     }
     
     @Override
     public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

    	 System.out.println("11111111111   "+ id);
    	 Member member = memberRepository.findByMemberId(id);
    	 System.out.println("22222   "+ member);
    	 if(member == null){
    		 throw new UsernameNotFoundException(id);
    	 }

    	 return User.builder()
    			 .username(member.getMemberId())
    			 .password(member.getPassword())
//    			 .roles(member.getRole().toString())
    			 .build();
     }

}