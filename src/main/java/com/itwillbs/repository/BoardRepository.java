package com.itwillbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{

	// JpaRepository<T(Entity), ID(primary key type)>
	// JpaRepository 지원하는 메서드
	// save(Entity) : 엔티티 저장 및 수정
	// void delete(Entity) : 엔티티 삭제
	// count : 엔티티 총 개수 반환
	// List<Entity> findAll() : 모든 엔티티 조회
	// Entity findById(id) : id에 대한 엔티티 조회
	


	
}
