package com.itwillbs.controller;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.entity.Member;
import com.itwillbs.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@RequiredArgsConstructor
@Log
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping("/insert")
	public String insert() {
		log.info("MemberController insert()");
		return "/member/insert";
	}
	
	@PostMapping("/insert")
	public String insertPost(Member member) {
		log.info("MemberController insertPost()");
		
		memberService.insertMember(member);
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		log.info("MemberController login()");
		return "/member/login";
	}
	
//	@PostMapping("/login")
//	public String loginPost(Member member, HttpSession session) {
//		log.info("MemberController loginPost()");
//		
//		Member meberDB = memberService.checkUser(member);
//		if(meberDB == null) {
//			return "redirect:/login";
//		}else {
//			session.setAttribute("memberID", meberDB.getId());
//			return "redirect:/main";
//		}
//	}
	
	@GetMapping("/main")
	public String main() {
		log.info("MemberController main()");
		log.info(SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal().toString());
		
		String id = SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		log.info(id);
		
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getAuthorities();
		
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		log.info(role);
		
		return "/member/main";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		log.info("MemberController logout()");
		
		session.invalidate();
		
		return "redirect:/main";
	}
	
	@GetMapping("/info")
	public String info(Model model, HttpSession session) {
		log.info("MemberController info()");
//		log.info((String) session.getAttribute("memberID"));
		String id = SecurityContextHolder
				.getContext().getAuthentication().getName();
		
		
		Optional<Member> member = memberService.findById(id);
		model.addAttribute("member", member.get());
		
		return "/member/info";
	}
	
	@GetMapping("/update")
	public String update(Model model) {
		log.info("MemberController update()");
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Member> member = memberService.findById(id);
		model.addAttribute("member", member.get());
		
		return "/member/update";
	}
	
	@PostMapping("/update")
	public String updatePost(Member member) {
		log.info("MemberController updatePost()");
		
		Member memberDB = null;
		try {
			memberDB = memberService.checkUser(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(memberDB == null) {
			return "redirect:/update";
		}else {
			member.setDate(memberDB.getDate());
			member.setPass(memberDB.getPass());
			member.setRole(memberDB.getRole());
			memberService.updateMember(member);
			
			return "redirect:/main";
		}
	}
	
	@GetMapping("/delete")
	public String delete(Model model, HttpSession session) {
		log.info("MemberController delete()");
		
		return "/member/delete";
	}
	
	@PostMapping("/delete")
	public String deletePost(Member member, HttpSession session) {
		log.info("MemberController delete()");
		
		Member memberDB = null;
		try {
			memberDB = memberService.checkUser(member);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(memberDB == null) {
			return "redirect:/delete";
		}else {
			memberService.deleteMember(member);
			session.invalidate();
			
			return "redirect:/logout";
		}
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		log.info("MemberController list()");
		
		List<Member> memberList = memberService.getMemberList();
		model.addAttribute("memberList", memberList);
		
		return "/member/list";
	}
}
