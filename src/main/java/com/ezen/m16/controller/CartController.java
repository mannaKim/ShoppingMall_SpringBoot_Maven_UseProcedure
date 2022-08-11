package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.m16.service.CartService;

@Controller
public class CartController {
	
	@Autowired
	CartService cs;
	
	@RequestMapping("cartInsert")
	public String cart_insert(
			HttpServletRequest request,
			@RequestParam("pseq") int pseq,
			@RequestParam("quantity") int quantity) { 
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		
		if(loginUser == null)
			return "member/login";
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", loginUser.get("ID"));
			paramMap.put("pseq", pseq);
			paramMap.put("quantity", quantity);
			cs.insertCart(paramMap);
		}
		return "redirect:/cartList";
	}
	
	@RequestMapping("/cartList")
	public ModelAndView cart_list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
		= (HashMap<String, Object>)session.getAttribute("loginUser");
	
		if(loginUser == null)
			mav.setViewName("member/login");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", loginUser.get("ID"));
			paramMap.put("ref_cursor", null);
			cs.listCart(paramMap);
			
			ArrayList<HashMap<String,Object>> list
				= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
			mav.addObject("cartList", list);
			
			int totalPrice = 0;
			for(HashMap<String,Object> cart : list) {
				totalPrice += Integer.parseInt(cart.get("QUANTITY").toString())
									* Integer.parseInt(cart.get("PRICE2").toString());
			}
			mav.addObject("totalPrice", totalPrice);
			
			mav.setViewName("mypage/cartList");
		}
		return mav;
	}
	
	@RequestMapping("/cartDelete")
	public String cart_delete(@RequestParam("cseq") String [] cseqArr) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cseqArr", cseqArr);
		cs.deleteCart(paramMap);
		return "redirect:/cartList";
	}
}
