package com.itwillbs.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.entity.Board;
import com.itwillbs.entity.Customer;
import com.itwillbs.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@RequiredArgsConstructor
@Log
public class BoardController {
	@Value("${uploadPath}")
	String uploadPath;
	
	private final BoardService boardService;
	
	@GetMapping("/boardWrite")
	public String boardWrite() {
		log.info("BoardController boardWrite()");
		
		
		
		return "/board/write";
	}
	
	@PostMapping("/boardWrite")
	public String boardWritePost(@RequestParam("name") String name,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file) throws IOException {
		log.info("BoardController boardWritePost()");
		log.info(name);
		log.info(subject);
		log.info(content);
		log.info(file.getOriginalFilename());
		
		UUID uuid = UUID.randomUUID();
		String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		log.info(extension); //  확장자  .jpg
		String filename = uuid.toString()+extension;
		log.info(filename);
		
		String fileUploadFullUrl = uploadPath + "/" + filename;
		log.info(fileUploadFullUrl);
		
		FileCopyUtils.copy(file.getBytes(), new File(uploadPath,filename));
		
		Board board = new Board();
		board.setName(name);
		board.setSubject(subject);
		board.setContent(content);
		board.setFile(filename);
		
		boardService.insertBoard(board);
		
		return "redirect:/boardList";
	}
	
	@GetMapping("/boardList")
	public String boardList(Model model,
			@RequestParam(value = "page", defaultValue = "1", required = false)int page,
			@RequestParam(value = "size", defaultValue = "3", required = false)int size) {
		log.info("BoardController boardList()");
		//page : 페이지 번호
		//size : 한 페이지의 행 갯수
//		PageRequest.of(page, size);
		Pageable pageable = PageRequest.of(page-1, size, Sort.by("num").descending());
		
		Page<Board> boardList = boardService.getBoardList(pageable);
		
		model.addAttribute("boardList",boardList);
		log.info(""+boardList.getTotalElements());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageSize", size);
		//전체 페이지 개수
		model.addAttribute("totalPages", boardList.getTotalPages());
		
		int pageBlock =3;
		int startPage = (page-1)/pageBlock * pageBlock + 1;
		int endPage = startPage + pageBlock - 1;
		if(endPage > boardList.getTotalPages()) {
			endPage = boardList.getTotalPages();
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		
		return "/board/list";
	}//boardList()
	
	@GetMapping("/content")
	public String content(Model model,
			@RequestParam(value = "num", defaultValue = "1", required = false)int num) {
		log.info("BoardController content()");
		
		Optional<Board> board = boardService.getBoard(num);
		model.addAttribute("board", board.get());
		
		return "/board/content";
	}

	@GetMapping("/boardUpdate")
	public String boaradUpdate(Model model,
			@RequestParam(value = "num", defaultValue = "1", required = false)int num) {
		log.info("BoardController boaradUpdate()");
		
		Optional<Board> board = boardService.getBoard(num);
		model.addAttribute("board", board.get());
		
		return "/board/update";
	}
	
	@PostMapping("/boardUpdate")
	public String boaradUpdatePost(Model model,
			Board board) {
		log.info("BoardController boaradUpdatePost()");
		
		boardService.updateBoard(board);
		
		return "redirect:/content?num="+board.getNum();
	}
	@GetMapping("/boardDelete")
	public String boardDelete(@RequestParam(value = "num", defaultValue = "1", required = false)int num) {
		log.info("BoardController boardDelete()");
		
		boardService.deletBoard(num);
		
		return "redirect:/boardList";
	}
}
