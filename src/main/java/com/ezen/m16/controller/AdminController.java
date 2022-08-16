package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.m16.dto.Paging;
import com.ezen.m16.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	AdminService as;
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin/adminLoginForm";
	}
	
	@RequestMapping("/adminLogin")
	public String admin_login(HttpServletRequest request, Model model,
			@RequestParam("workId") String workId, @RequestParam("workPwd") String workPwd) {
		String url = "admin/adminLoginForm";
		
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("ref_cursor", null);
		paramMap.put("workId", workId);
		as.getAdmin(paramMap);
		ArrayList<HashMap<String,Object>> list
			= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
		if(list.size()==0) {
			model.addAttribute("message", "아이디 없음");
			return url;
		}
		HashMap<String,Object> resultMap = list.get(0);
		if(resultMap.get("PWD")==null) {
			model.addAttribute("message", "관리자에게 문의하세요.");
		}else if(!workPwd.equals((String)resultMap.get("PWD"))) {
			model.addAttribute("message", "비밀번호를 확인하세요.");
		}else if(workPwd.equals((String)resultMap.get("PWD"))) {
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", resultMap);
			url = "redirect:/productList";
		}
		return url;
	}
	
	@RequestMapping("/adminLogout")
	public String admin_logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginAdmin");
		return "admin/adminLoginForm";
	}
	
	@RequestMapping("/productList")
	public ModelAndView product_list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		if(session.getAttribute("loginAdmin")==null)
			mav.setViewName("admin/adminLoginForm");
		else {
			int page = 1;
			if(request.getParameter("page")!=null) {
				page = Integer.parseInt(request.getParameter("page"));
				session.setAttribute("page", page);
			}else if(session.getAttribute("page")!=null) {
				page = (Integer)session.getAttribute("page");
			}else {
				session.removeAttribute("page");
			}
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ref_cursor", null);
			as.getProductList(paramMap, page);
			ArrayList<HashMap<String,Object>> list
				= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
			mav.addObject("productList", list);
			mav.addObject("paging", (Paging)paramMap.get("paging"));
			mav.setViewName("admin/product/productList");
		}
		return mav;
	}
}
