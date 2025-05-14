package com.honsoft.shopmall.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.honsoft.shopmall.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="members")
@Data
@NoArgsConstructor
public class Member {

    @Id
    @Column(name="num")  
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long num;
    
    @Column(unique = true)
    private String memberId;
    
    private String password;
    
    private String name;   
      
    private String phone;
  
    private String email;

    private String address;

//    @Enumerated(EnumType.STRING)
//    private Role role;

  
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setMemberId(memberFormDto.getMemberId());
        member.setName(memberFormDto.getName());
        member.setPhone(memberFormDto.getPhone());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
//        member.setRole(Role.USER);
        return member;
    }
    
}