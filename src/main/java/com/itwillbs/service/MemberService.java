package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.entity.Member;
import com.itwillbs.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class MemberService {
	
	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;
	
	public void insertMember(Member member) {
		log.info("MemberService insertMember()");
//		member.setDate(new Timestamp(System.currentTimeMillis()));
		
		Member memberEnc = member.createUser(
				member.getId(), 
				member.getPass(), 
				passwordEncoder, 
				member.getName()
				);
		
		memberRepository.save(memberEnc);
	}

	public Member checkUser(Member member) throws Exception {
		log.info("MemberService checkUser()");
		String pass = passwordEncoder.encode(member.getPass());
		Member memberDB = memberRepository.findById(member.getId())
				.orElseThrow(() -> new Exception("회원없음"));
		log.info(memberDB.toString());
		
		boolean match = passwordEncoder.matches(member.getPass(), memberDB.getPass());
		System.out.println("passwordEncoder.matches : " + match);
		if(match == true) {
			//비밀번호 일치
			return memberDB;
		}else {
			//비밀번호 틀림 
			return null;
		}
	}

	public Optional<Member> findById(String id) {
		log.info("MemberService findById()");
		return memberRepository.findById(id);
	}

	public void updateMember(Member member) {
		log.info("MemberService updateMember()");
		memberRepository.save(member);
	}

	public void deleteMember(Member member) {
		log.info("MemberService deleteMember()");
		 memberRepository.deleteById(member.getId());
	}

	public List<Member> getMemberList() {
		log.info("MemberService getMemberList()");
		return memberRepository.findAll();
	}
}
