package com.ezen.m16.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ezen.m16.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	OrderService os;
	
	@RequestMapping("/orderInsert")
	public String order_insert(HttpServletRequest request) {
		String oseq = "";
		
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			return "member/login";
		else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", loginUser.get("ID"));
			paramMap.put("oseq", 0);
			os.insertOrder(paramMap);
			// 아이디로 카트 검색
			// 검색 내용으로 orders와 order_detail 테이블에 레코드 추가
			// 카트 테이블 레코드 삭제
			// oseq에 주문번호를 넣어 되돌아옴
			oseq = paramMap.get("oseq").toString();
		}
		return "redirect:/orderList?oseq="+oseq;
	}
	
	@RequestMapping("/orderList")
	public ModelAndView order_list(HttpServletRequest request, @RequestParam("oseq") int oseq) {
		ModelAndView mav =  new ModelAndView();
		
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			mav.setViewName("member/login");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ref_cursor", null);
			paramMap.put("oseq", oseq);
			os.listOrder(paramMap);
			
			ArrayList<HashMap<String, Object>> list
				= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			mav.addObject("orderList", list);
			
			int totalPrice = 0;
			for(HashMap<String,Object> order : list) {
				totalPrice += Integer.parseInt(order.get("QUANTITY").toString())
									* Integer.parseInt(order.get("PRICE2").toString());
			}
			mav.addObject("totalPrice", totalPrice);
			
			mav.setViewName("mypage/orderList");
		}
		return mav;
	}
	
	@RequestMapping(value="/orderInsertOne", method=RequestMethod.POST)
	public String orderInsertOne(HttpServletRequest request,
			@RequestParam("pseq") int pseq,
			@RequestParam("quantity") int quantity) {
		String oseq = "";
		
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			return "member/login";
		else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", loginUser.get("ID"));
			paramMap.put("pseq", pseq);
			paramMap.put("quantity", quantity);
			paramMap.put("oseq", 0);
			os.insertOrderOne(paramMap);
			oseq = paramMap.get("oseq").toString();
		}
		return "redirect:/orderList?oseq="+oseq;
	}
	
	@RequestMapping("/myPage")
	public ModelAndView myPage(HttpServletRequest request) {
		ModelAndView mav =  new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			mav.setViewName("member/login");
		else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", loginUser.get("ID"));
			os.myPageList(paramMap);
			
			ArrayList<HashMap<String, Object>> finalList
				= (ArrayList<HashMap<String, Object>>)paramMap.get("finalList");
			mav.addObject("orderList", finalList);
			
			mav.addObject("title", "진행중인 주문내역");
			mav.setViewName("mypage/mypage");
		}
		return mav;
	}
	
	@RequestMapping("/orderDetail")
	public ModelAndView order_detail(HttpServletRequest request, @RequestParam("oseq") int oseq) {
		ModelAndView mav =  new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			mav.setViewName("member/login");
		else {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			os.listOrder(paramMap);
			
			ArrayList<HashMap<String, Object>> list
				= (ArrayList<HashMap<String, Object>>)paramMap.get("ref_cursor");
			
			int totalPrice = 0;
			for(HashMap<String,Object> order : list) {
				totalPrice += Integer.parseInt(order.get("QUANTITY").toString())
									* Integer.parseInt(order.get("PRICE2").toString());
			}
			
			mav.addObject("orderList", list);
			mav.addObject("totalPrice", totalPrice);
			mav.addObject("orderDetail", list.get(0));
			mav.setViewName("mypage/orderDetail");
		}
		return mav;
	}	
	
	@RequestMapping("/orderAll")
	public ModelAndView order_all(HttpServletRequest request) {
		ModelAndView mav =  new ModelAndView();
		HttpSession session = request.getSession();
		HashMap<String, Object> loginUser
			= (HashMap<String, Object>)session.getAttribute("loginUser");
		if(loginUser == null)
			mav.setViewName("member/login");
		else {
			HashMap<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", loginUser.get("ID"));
			os.orderAllList(paramMap);
			
			ArrayList<HashMap<String, Object>> finalList
				= (ArrayList<HashMap<String, Object>>)paramMap.get("finalList");
			mav.addObject("orderList", finalList);
			
			mav.addObject("title", "총 주문내역");
			mav.setViewName("mypage/mypage");
		}
		return mav;
	}
}
