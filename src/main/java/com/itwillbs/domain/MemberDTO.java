package com.itwillbs.domain;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberDTO {
	private String id;
	private String pass;
	private String name;
	private Timestamp date;
	
}
