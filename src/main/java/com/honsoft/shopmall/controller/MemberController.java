package com.honsoft.shopmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.dto.MemberFormDto;
import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.service.MemberService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/members")
public class MemberController {

	
	@Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    
    @GetMapping(value = "/add")
    public String requestAddMemberForm(Model model){
    	
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "members/memberAdd";
    }
    
    @PostMapping(value = "/add")
    public String submitAddNewMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "members/memberAdd";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
       
            return "members/memberAdd";
        }

        return "redirect:/members";
    }

    
    @GetMapping(value = "/update/{memberId}")
    public String requestUpdateMemberForm(@PathVariable(name = "memberId") String memberId, Model model){
    	Member member = memberService.getMemberById(memberId);    	
        model.addAttribute("memberFormDto", member);
        return "members/updateMember";
    }
    
    @PostMapping(value = "/update")
    public String submitUpdateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "members/memberUpdate";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "members/memberAdd";
        }

        return "redirect:/members";
    }
    
    @GetMapping("/delete/{memberId}")
  	public String deleteMember(@PathVariable(name = "memberId") String memberId) {
    	memberService.deleteMember(memberId);
  		
    	return "redirect:/logout";
  	}
    
    @GetMapping
    public String requestMain(){
    	return "redirect:/";
    }
}
