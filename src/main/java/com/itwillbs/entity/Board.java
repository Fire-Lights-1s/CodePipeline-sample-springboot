package com.itwillbs.entity;


import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="board")
@Getter
@Setter
@ToString
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num")
	private int num;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "readCount")
	private int readCount;
	
	@Column(name = "date")
	private Timestamp date;

	@Column(name = "file")
	private String file;
}
