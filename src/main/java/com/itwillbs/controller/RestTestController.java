package com.itwillbs.controller;

import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.domain.MemberDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class RestTestController {
	@GetMapping("/rTest")
	public String rTest() {
		return "Hello, Spring boot";
	}
	
	@GetMapping("/rTest2")
	public String rTest2() {
		return "rTest2 Content";
	}
	
	@GetMapping("/rTest3")
	public MemberDTO rTest3() {
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("kim");
		memberDTO.setPass("p123");
		memberDTO.setName("김길동");
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
		
		return memberDTO;
	}
	
	@GetMapping("/rTest4")
	public List<MemberDTO> rTest4() {
		List<MemberDTO> members = new ArrayList<MemberDTO>();
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("kim");
		memberDTO.setPass("p123");
		memberDTO.setName("김길동");
		memberDTO.setDate(new Timestamp(System.currentTimeMillis()));
		for(int i=0; i< 5; i++) {
			members.add(memberDTO);
		}
		
		return members;
	}
	
}
