package com.itwillbs.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="customer")
@Getter
@Setter
//양방향 연관 관계 때문에 순환 문제가 발생함
//@ToString 
public class Customer {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "customer")
	private List<Food> foodList = new ArrayList<>();
	
}
