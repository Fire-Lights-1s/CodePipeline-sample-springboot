package com.itwillbs.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Food;
import com.itwillbs.entity.Member;
import com.itwillbs.repository.FoodRepository;
import com.itwillbs.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@RequiredArgsConstructor
@Log
public class TestController {
	
	private final FoodRepository foodRepository;
	
	private final MemberRepository memberRepository;
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	@GetMapping("/test2")
	public String test2() {
		return "test2";
	}
	
	@GetMapping("/test4")
	public String test4(Model model) {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("kim");
		memberDTO.setPass("p123");
		memberDTO.setName("김길동");
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
		
		model.addAttribute("memberDTO",memberDTO);
		
		return "test4";
	}
	
	@GetMapping("/test5")
	public String test5(Model model) {
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		for(int i=0; i< 5; i++) {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId("kim"+i);
			memberDTO.setPass("p123");
			memberDTO.setName("김길동");
			memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
			memberList.add(memberDTO);
		}
		model.addAttribute("memberList",memberList);
		
		return "test5";
	}
	
	@GetMapping("/test6")
	public String test6(Model model, MemberDTO memberDTO) {
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
		model.addAttribute("memberDTO",memberDTO);
		
		return "test6";
	}
	@GetMapping("/test7")
	public String test7(Model model) {
		log.info("test7 save()");
		Member member = new Member();
		member.setId("kim");
		member.setPass("p123");
		member.setName("김길동");
		member.setDate(new Timestamp(System.currentTimeMillis()));
		
		memberRepository.save(member);
		
		
		return "test";
	}
	
	@GetMapping("/mapping")
	public String mapping() {
		log.info("mapping mapping()");
		
		List<Food> foodList =foodRepository.findAll();
		log.info(foodList.toString());
		
		return "index";
	}
	
}
