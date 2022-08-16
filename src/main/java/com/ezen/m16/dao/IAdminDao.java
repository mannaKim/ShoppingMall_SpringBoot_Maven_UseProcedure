package com.ezen.m16.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAdminDao {

	void getAdmin(HashMap<String, Object> paramMap);

	void getProductList(HashMap<String, Object> paramMap);

	void adminGetAllCount(HashMap<String, Object> cntMap);

}
