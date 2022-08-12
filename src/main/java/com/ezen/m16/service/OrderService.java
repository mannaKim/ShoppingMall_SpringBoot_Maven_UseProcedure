package com.ezen.m16.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.m16.dao.IOrderDao;

@Service
public class OrderService {
	
	@Autowired
	IOrderDao odao;

	public void insertOrder(HashMap<String, Object> paramMap) {
		odao.insertOrder(paramMap);
	}

	public void listOrder(HashMap<String, Object> paramMap) {
		odao.listOrder(paramMap);
	}

	public void insertOrderOne(HashMap<String, Object> paramMap) {
		odao.insertOrderOne(paramMap);
	}

	public void myPageList(HashMap<String, Object> paramMap) {
		//mypage.jsp에 전달될 리스트
		ArrayList<HashMap<String,Object>> finalList
			= new ArrayList<HashMap<String,Object>>();
		
		paramMap.put("ref_cursor", null);
		
		//전달된 아이디로 진행중인 주문번호들을 조회
		odao.listOrderByIdIng(paramMap);
		ArrayList<HashMap<String,Object>> oseqList
			= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
		
		//주문번호별 주문내역을 조회
		for(HashMap<String,Object> result : oseqList) {
			int oseq = Integer.parseInt(result.get("OSEQ").toString());
			paramMap.put("ref_cursor", null);
			paramMap.put("oseq", oseq);
			odao.listOrder(paramMap);
			
			ArrayList<HashMap<String,Object>> orderList
				= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");
			
			HashMap<String,Object> orderFirst = orderList.get(0); 
			//주문 상품 중 첫번째 상품을 대표 상품으로 이름과 가격을 주문 전체에 대한 내용으로 변경
			orderFirst.put("PNAME", (String)orderFirst.get("PNAME")+"포함"+orderList.size()+"건");
			int totalPrice = 0;
			for(HashMap<String,Object> order : orderList) {
				totalPrice += Integer.parseInt(order.get("QUANTITY").toString())
						* Integer.parseInt(order.get("PRICE2").toString());
			}
			orderFirst.put("PRICE2", totalPrice);
			
			finalList.add(orderFirst);
		}
		paramMap.put("finalList", finalList);
	}

	public void orderAllList(HashMap<String, Object> paramMap) {
		ArrayList<HashMap<String,Object>> finalList = new ArrayList<HashMap<String,Object>>();
		paramMap.put("ref_cursor", null);

		odao.listOrderByIdAll(paramMap);
		ArrayList<HashMap<String,Object>> oseqList
		= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");

		for(HashMap<String,Object> result : oseqList) {
			int oseq = Integer.parseInt(result.get("OSEQ").toString());
			paramMap.put("ref_cursor", null);
			paramMap.put("oseq", oseq);
			odao.listOrder(paramMap);

			ArrayList<HashMap<String,Object>> orderList
				= (ArrayList<HashMap<String,Object>>)paramMap.get("ref_cursor");

			HashMap<String,Object> orderFirst = orderList.get(0); 
			orderFirst.put("PNAME", (String)orderFirst.get("PNAME")+"포함"+orderList.size()+"건");
			int totalPrice = 0;
			for(HashMap<String,Object> order : orderList) {
				totalPrice += Integer.parseInt(order.get("QUANTITY").toString())
						* Integer.parseInt(order.get("PRICE2").toString());
			}
			orderFirst.put("PRICE2", totalPrice);
			finalList.add(orderFirst);
		}
		paramMap.put("finalList", finalList);
	}
}
