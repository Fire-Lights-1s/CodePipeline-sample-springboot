package com.itwillbs.entity;


import java.sql.Timestamp;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="members")
@Getter
@Setter
@ToString
public class Member {
	@Id
	@Column(name = "id", length= 50)
	private String id;
	
	@Column(name = "pass", nullable = false)
	private String pass;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "date")
	private Timestamp date;
	
	@Column(name = "role")
	private String role;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;
	
	public Member(Team team) {
        this.team = team;
    }
	
	public Member() {
		
	}
	
	public Member(String id, String pass, String name, Timestamp date, String role) {
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.date = date;
		this.role = role;
	}
	
	public static Member createUser(String id, String pass, PasswordEncoder passwordEncoder, String name) {
		if(id.equals("admin")) {
			return new Member(id
					, passwordEncoder.encode(pass)
					, name
					, new Timestamp(System.currentTimeMillis())
					, "ADMIN");
		}else{
			return new Member(id
					, passwordEncoder.encode(pass)
					, name
					, new Timestamp(System.currentTimeMillis())
					, "USER");
		}
	}

}
