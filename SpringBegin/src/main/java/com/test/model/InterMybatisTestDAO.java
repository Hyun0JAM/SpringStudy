package com.test.model;

import java.util.HashMap;
import java.util.List;

public interface InterMybatisTestDAO {
	
	int mbtest1();
	int mbtest2(String name);
	int mbtest3(MybatisTestVO vo);
	int mbtest4(MemberVO mvo);
	int mbtest5(HashMap<String, String> map);
	String mbtest6(int no);
	public MemberVO mbtest7(int no);
	public List<MemberVO> mbtest8(String addr);
	public List<MemberVO2> mbtest9(String addr);
	public List<MemberVO2> mbtest9_2(String addr);
	public List<HashMap<String, String>> mbtest10(String addr);
	List<HashMap<String, String>> mbtest11(HashMap<String, String> paraMap);
	List<Integer> mbtest12_deptno();
	List<HashMap<String, String>> mbtest12(HashMap<String, String> paraMap);
	List<HashMap<String, String>> mbtest13(String addrSearch);
	/////////////
	int ex01(String date);
	List<String> ex02();
	List<HashMap<String, Object>> ex02JSON(String job_id);
	List<HashMap<String, Object>> mbtest14(HashMap<String, Object> paraMap);
	List<HashMap<String, Object>> mbtest15_gender();
	List<HashMap<String, Object>> mbtest15_ageline();
	List<HashMap<String, Object>> mbtest15_deptno();
	List<HashMap<String, Object>> mbtest15_emp(String deptno);
}
