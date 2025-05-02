package com.honsoft.shopmall.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.honsoft.shopmall.entity.Member;
import com.honsoft.shopmall.repository.MemberRepository;


@Controller
@RequestMapping("/members")
public class MemberController {
	
	private final MemberRepository repository;
	
	public MemberController(MemberRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public String viewHomePage(Model model) {		 
				 
//		Iterable<Member> memberList = repository.findAll();		
		Iterable<Member> memberList = repository.selectMethod();		
		
		model.addAttribute("memberList", memberList);
		return "members/memberList";
	}
	
	@GetMapping("/new")
	public String newMethod(Model model) {
		Member member = new Member();
		model.addAttribute("member", member);
		
		return "members/memberAdd";
	}
	
	@PostMapping("/insert")
	public String insertMethod(@ModelAttribute("member") Member member) {
		
//		repository.save(member);
		repository.insertMethod(member.getName(), member.getAge(), member.getEmail());
		return "redirect:/members";
	}
	
	@GetMapping("/edit/{id}")
	public String editMethod(@PathVariable(name = "id") Long id, Model model) {
				
//		Optional<Member> member = repository.findById(id);
		Member member = repository.selectMethodById(id);
	
		 model.addAttribute("member", member);
		
		return "members/memberEdit";
	}	
	
	@PostMapping("/update")
	public String updateMethod(@ModelAttribute("member") Member member) {
		
		
//		repository.save(member);	
		repository.updateMethod(member.getName(), member.getAge(), member.getEmail(), member.getId());
		return "redirect:/members";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteMethod(@PathVariable(name = "id") Long id) {
			
//		repository.deleteById(id);
		repository.deleteMethod(id);
		
		return "redirect:/members";
	}

}
