package com.itwillbs;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.itwillbs.entity.Member;
import com.itwillbs.entity.Team;
import com.itwillbs.repository.MemberRepository;
import com.itwillbs.repository.TeamRepository;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class JTest {
	
	@Autowired
    private MemberRepository memberRepository;
	@Autowired
	private TeamRepository teamRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
	@DisplayName("1 + 2 = 3")
	public void test() {
		int a = 1;
		int b = 2;
		int sum = 3;
		Assertions.assertEquals(sum, a+b);
	}
	@DisplayName("1 + 3 = 4")
	public void test2() {
		int a = 1;
		int b = 3;
		int sum = 3;
		Assertions.assertEquals(sum, a+b);
	}
	
	public void jpaTest() {
		Team team = new Team();
		team.setName("test1");
		
		teamRepository.save(team);
		
		Member member = new Member();
		member.setId("Test");
		member.setPass(encoder.encode("test123"));
		member.setName("testName");
		member.setDate(new Timestamp(System.currentTimeMillis()));
		member.setRole("USER");
		member.setTeam(null);
		
		memberRepository.save(member);
		
		memberRepository.findAll();
		
	}
	
	@Test
	public void jpaFindTest(){
		Member member = new Member();
		member.setId("Test");
		member.setPass(encoder.encode("test123"));
		member.setName("testName");
		member.setDate(new Timestamp(System.currentTimeMillis()));
		member.setRole("USER");
		member.setTeam(null);
		
		memberRepository.save(member);
		
		List<Member> members = memberRepository.findAll();
		for(Member member2 : members) {
			log.info(""+member);
		}
		
	}
}
