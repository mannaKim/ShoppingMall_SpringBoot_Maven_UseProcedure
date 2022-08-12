package com.ezen.m16.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQnaDao {

	void getAllCount(HashMap<String, Object> paramMap);

	void listQna(HashMap<String, Object> paramMap);

	void insertQna(HashMap<String, Object> paramMap);

	void getQna(HashMap<String, Object> paramMap);

}
