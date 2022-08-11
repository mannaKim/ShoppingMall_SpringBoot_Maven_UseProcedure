package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.m16.dto.MemberVO;
import com.ezen.m16.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	MemberService ms;
	
	@RequestMapping("/loginForm")
	public String login_form() {
		return "member/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(
			@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result,
			HttpServletRequest request,
			Model model
			) {
		String url = "member/login";
		if(result.getFieldError("id") != null) {
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
		}else if(result.getFieldError("pwd") != null) {
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		}else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", membervo.getId());
			paramMap.put("ref_cursor", null);
			ms.getMember(paramMap);
			
			ArrayList<HashMap<String, Object>> list
			= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			
			if(list.size() == 0) {
				model.addAttribute("message", "아이디가 없습니다.");
				return url;
			}
			
			HashMap<String, Object> mvo = list.get(0);
			if(mvo.get("PWD") == null)
				model.addAttribute("message", "관리자에게 문의하세요.");
			else if(!mvo.get("PWD").equals(membervo.getPwd()))
				model.addAttribute("message", "비밀번호가 틀립니다.");
			else if(mvo.get("PWD").equals(membervo.getPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", mvo);
				url = "redirect:/";
			}
		}
		return url;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		return "redirect:/";
	}
	
}
