package com.itwillbs.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itwillbs.entity.Board;
import com.itwillbs.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class BoardService {
	
	private final BoardRepository boardRepository;

	public void insertBoard(Board board) {
		log.info("BoardService insertBoard()");
		board.setReadCount(0);
		board.setDate(new Timestamp(System.currentTimeMillis()));
		
		boardRepository.save(board);
	}

	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	public Optional<Board> getBoard(int num) {
		return boardRepository.findById(num);
	}

	public void updateBoard(Board board) {
		boardRepository.save(board);
	}

	public void deletBoard(int num) {
		boardRepository.deleteById(num);
	}

}
