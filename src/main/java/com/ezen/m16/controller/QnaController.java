package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.m16.dto.Paging;
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
}
