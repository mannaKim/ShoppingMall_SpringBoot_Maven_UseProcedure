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
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping("/contract")
	public String contract() {
		return "member/contract";
	}
	
	@RequestMapping(value="joinForm", method=RequestMethod.POST)
	public String join_form(Model model, HttpServletRequest request) {
		return "member/joinForm";
	}
	
	@RequestMapping("idCheckForm")
	public String id_check_form(
			@RequestParam("id") String id,
			Model model, HttpServletRequest request) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		paramMap.put("ref_cursor", null);
		ms.getMember(paramMap);
		ArrayList<HashMap<String, Object>> list
		= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
		
		if(list.size() == 0) 
			model.addAttribute("result", -1);
		else
			model.addAttribute("result", 1);
		
		model.addAttribute("id", id);
		
		return "member/idcheck";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(
			@ModelAttribute("dto") @Valid MemberVO membervo,
			BindingResult result,
			@RequestParam(value="reid", required=false) String reid,
			@RequestParam(value="pwdCheck", required=false) String pwdCheck,
			Model model,
			HttpServletRequest request) 
	{
		String url = "member/joinForm";
		
		if(result.getFieldError("id")!=null) 
			model.addAttribute("message", result.getFieldError("id").getDefaultMessage());
		else if(result.getFieldError("pwd")!=null) 
			model.addAttribute("message", result.getFieldError("pwd").getDefaultMessage());
		else if(result.getFieldError("name")!=null) 
			model.addAttribute("message", result.getFieldError("name").getDefaultMessage());
		else if(result.getFieldError("email")!=null) 
			model.addAttribute("message", result.getFieldError("email").getDefaultMessage());
		else if(result.getFieldError("phone")!=null) 
			model.addAttribute("message", result.getFieldError("phone").getDefaultMessage());
		else if(reid == null || !reid.equals(membervo.getId())) 
			model.addAttribute("message", "아이디 중복 체크가 되지 않았습니다.");
		else if(pwdCheck == null || !pwdCheck.equals(membervo.getPwd())) 
			model.addAttribute("message", "비밀번호 확인이 일치하지 않습니다.");
		else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", membervo.getId());
			paramMap.put("pwd", membervo.getPwd());
			paramMap.put("name", membervo.getName());
			paramMap.put("email", membervo.getEmail());
			paramMap.put("phone", membervo.getPhone());
			paramMap.put("zip_num", membervo.getZip_num());
			paramMap.put("address1", membervo.getAddress1());
			paramMap.put("address2", membervo.getAddress2());
			paramMap.put("address3", membervo.getAddress3());
			ms.insertMember(paramMap);
			model.addAttribute("message","회원가입 완료. 로그인하세요.");
			url = "member/login";
		}
		model.addAttribute("reid", reid);
		return url;
	}
}
