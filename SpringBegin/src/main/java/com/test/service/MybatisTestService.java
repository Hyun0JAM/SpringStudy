package com.test.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.model.MemberVO;
import com.test.model.MemberVO2;
import com.test.model.MybatisTestDAO;
import com.test.model.MybatisTestVO;

@Service
public class MybatisTestService {
	
	// ※ 의존객체주입(DI : Dependency Injection) 
		//  ==> 스프링 프레임워크는 객체를 관리해주는 컨테이너를 제공해주고 있다.
		//      스프링 컨테이너는 bean으로 등록되어진 MyBatisTestService 클래스 객체가 사용되어질때, 
		//      MyBatisTestService 클래스의 인스턴스 객체변수(의존객체)인 MyBatisTestDAO dao 에 
		//      자동적으로 bean 으로 등록되어 생성되어진 MyBatisTestDAO dao 객체를  
		//      MyBatisTestService 클래스의 인스턴스 변수 객체로 사용되어지게끔 넣어주는 것을 의존객체주입(DI : Dependency Injection)이라고 부른다. 
		//      이것이 바로 IoC(Inversion of Control == 제어의 역전) 인 것이다.
		//      즉, 개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것에서 탈피하여 스프링은 컨테이너에 객체를 담아 두고, 
		//      필요할 때에 컨테이너로부터 객체를 가져와 사용할 수 있도록 하고 있다. 
		//      스프링은 객체의 생성 및 생명주기를 관리할 수 있는 기능을 제공하고 있으므로, 더이상 개발자에 의해 객체를 생성 및 소멸하도록 하지 않고
		//      객체 생성 및 관리를 스프링 프레임워크가 가지고 있는 객체 관리기능을 사용하므로 Inversion of Control == 제어의 역전 이라고 부른다.  
		//      그래서 스프링 컨테이너를 IoC 컨테이너라고도 부른다.
		
		//  === 느슨한 결합 ===
		//      스프링 컨테이너가 MyBatisTestService 클래스 객체에서 MyBatisTestDAO 클래스 객체를 사용할 수 있도록 
		//      만들어주는 것을 "느슨한 결합" 이라고 부른다.
		//      느스한 결합은 MyBatisTestService 객체가 메모리에서 삭제되더라도 MyBatisTestDAO dao 객체는 메모리에서 동시에 삭제되는 것이 아니라 남아 있다.


	@Autowired
	private MybatisTestDAO dao;
	// private MyBatisTestDAO dao = new MyBatisTestDAO();
		// === 단단한 결합(개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것) ===
		//    ==> MyBatisTestService 객체가 메모리에서 삭제 되어지면 MyBatisTestDAO dao 객체는 멤버변수이므로 메모리에서 자동적으로 삭제되어진다.
	
	public int mbtest1() {
		int n = dao.mbtest1();
		return n;
	}

	public int mbtest2(String name) {
		int n = dao.mbtest2(name);
		return n;
	}

	public int mbtest3(MybatisTestVO vo) {
		int n = dao.mbtest3(vo);
		return n;
	}

	public int mbtest4(MemberVO mvo) {
		int n = dao.mbtest4(mvo);
		return n;
	}

	public int mbtest5(HashMap<String, String> map) {
		int n = mbtest5(map);
		return n;
	}
	public String mbtest6(int no){
		String name = dao.mbtest6(no);
		return name;
	}

	public MemberVO mbtest7(int no) {
		MemberVO member = dao.mbtest7(no);
		return member;
	}

	public List<MemberVO> mbtest8(String addr) {
		List<MemberVO> memberList = dao.mbtest8(addr);
		return memberList;
	}

	public List<MemberVO2> mbtest9(String addr) {
		List<MemberVO2> memberList = dao.mbtest9(addr);
		return memberList;
	}

	public List<MemberVO2> mbtest9_2(String addr) {
		List<MemberVO2> memberList = dao.mbtest9_2(addr);
		return memberList;
	}

	public List<HashMap<String, String>> mbtest10(String addr) {
		List<HashMap<String, String>> memberList = dao.mbtest10(addr);
		return memberList;
	}

	public List<HashMap<String, String>> mbtest11(HashMap<String,String> paraMap) {
		List<HashMap<String,String>> memberList = dao.mbtest11(paraMap);
		return memberList;
	}

	public List<Integer> mbtest12_deptno() {
		List<Integer> deptnoList = dao.mbtest12_deptno();
		return deptnoList;
	}

	public List<HashMap<String, String>> mbtest12(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> empList = dao.mbtest12(paraMap);
		return empList;
	}

	public int ex01(String date) {
		int dday = dao.ex01(date);
		return dday;
	}

	public List<HashMap<String, String>> mbtest13(String addrSearch) {
		List<HashMap<String, String>> memberList = dao.mbtest13(addrSearch);
		return memberList;
	}

	public List<String> ex02() {
		List<String> empList = dao.ex02();
		return empList;
	}

	public List<HashMap<String, Object>> ex02JSON(String job_id) {
		List<HashMap<String, Object>> empList = dao.ex02JSON(job_id);
		return empList;
	}

	public List<HashMap<String, Object>> mbtest14(HashMap<String, Object> paraMap) {
		List<HashMap<String, Object>> empList = dao.mbtest14(paraMap);
		return empList;
	}

	public List<HashMap<String, Object>> mbtest15_gender() {
		List<HashMap<String, Object>> genderList = dao.mbtest15_gender();
		return genderList;
	}
	public List<HashMap<String, Object>> mbtest15_ageline() {
		List<HashMap<String, Object>> agelineList = dao.mbtest15_ageline();
		return agelineList;
	}

	public List<HashMap<String, Object>> mbtest15_deptno() {
		List<HashMap<String, Object>> deptnoList = dao.mbtest15_deptno();
		return deptnoList;
	}

	public List<HashMap<String, Object>> mbtest15_emp(String deptno) {
		List<HashMap<String, Object>> empList = dao.mbtest15_emp(deptno);
		return empList;
	}
}
