package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.m16.dto.Paging;
import com.ezen.m16.dto.QnaVO;
import com.ezen.m16.service.QnaService;

@Controller
public class QnaController {
	
	@Autowired
	QnaService qs;
	
	@RequestMapping("/qnaList")
	public ModelAndView qnaList(HttpServletRequest request) {
		ModelAndView mav =  new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String,Object> loginUser
			= (HashMap<String,Object>)session.getAttribute("loginUser");
		if(loginUser==null) {
			mav.setViewName("member/login");
		}else {
			int page=1;
			if(request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
				session.setAttribute("page", page);
			}else if(session.getAttribute("page")!=null) {
				page=(Integer)session.getAttribute("page");
			}else {
				session.removeAttribute("page");
			}
			
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", loginUser.get("ID"));
			paramMap.put("ref_cursor", null);
			qs.listQna(paramMap, page);
			
			ArrayList<HashMap<String,Object>> list
				= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
			mav.addObject("qnaList", list);
			mav.addObject("paging", (Paging)paramMap.get("paging"));
			mav.setViewName("qna/qnaList");
		}
		return mav;
	}
	
	@RequestMapping("/qnaWriteForm")
	public String qna_write_form(HttpServletRequest request) {
		HttpSession session = request.getSession();
		HashMap<String,Object> loginUser
			= (HashMap<String,Object>)session.getAttribute("loginUser");
		if(loginUser==null) {
			return "member/login";
		}
		else return "qna/qnaWrite";
	}

	@RequestMapping("/qnaWrite")
	public ModelAndView qna_write(HttpServletRequest request,
			@ModelAttribute("dto") @Valid QnaVO qnavo,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String,Object> loginUser
			= (HashMap<String,Object>)session.getAttribute("loginUser");
		if(loginUser==null) {
			mav.setViewName("member/login");
		}else {
			mav.setViewName("qna/qnaWrite");
			if(result.getFieldError("subject")!=null) 
				mav.addObject("message", result.getFieldError("subject").getDefaultMessage());
			else if(result.getFieldError("content")!=null) 
				mav.addObject("message", result.getFieldError("content").getDefaultMessage());
			else {
				HashMap<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("id", loginUser.get("ID"));
				paramMap.put("subject", qnavo.getSubject());
				paramMap.put("content", qnavo.getContent());
				qs.insertQna(paramMap);
				mav.setViewName("redirect:/qnaList");
			}
		}
		return mav;
	}
	
	@RequestMapping("/qnaView")
	public ModelAndView qna_view(HttpServletRequest request, @RequestParam("qseq") int qseq) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String,Object> loginUser
			= (HashMap<String,Object>)session.getAttribute("loginUser");
		if(loginUser==null) {
			mav.setViewName("member/login");
		}else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("qseq", qseq);
			paramMap.put("ref_cursor", null);
			qs.getQna(paramMap);
			
			ArrayList<HashMap<String,Object>> list
			= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
			
			mav.addObject("qnaVO", list.get(0));
			mav.setViewName("qna/qnaView");
		}
		return mav;
	}
}
