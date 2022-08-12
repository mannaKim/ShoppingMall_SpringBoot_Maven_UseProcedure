package com.ezen.m16.service;

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
}
