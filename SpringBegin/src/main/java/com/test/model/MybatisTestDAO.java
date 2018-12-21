package com.test.model;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisTestDAO implements InterMybatisTestDAO {
	//업무단(Business단)
	
	// ※ 의존객체주입(DI : Dependency Injection) 
		//  ==> 스프링 프레임워크는 객체를 관리해주는 컨테이너를 제공해주고 있다.
		//      스프링 컨테이너는 bean으로 등록되어진 MyBatisTestDAO 클래스 객체가 사용되어질때, 
		//      MyBatisTestDAO 클래스의 인스턴스 객체변수(의존객체)인 SqlSessionTemplate sqlsession 에 
		//      자동적으로 bean 으로 등록되어 생성되어진 SqlSessionTemplate sqlsession 객체를  
		//      MyBatisTestDAO 클래스의 인스턴스 변수 객체로 사용되어지게끔 넣어주는 것을 의존객체주입(DI : Dependency Injection)이라고 부른다. 
		//      이것이 바로 IoC(Inversion of Control == 제어의 역전) 인 것이다.
		//      즉, 개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것에서 탈피하여 스프링은 컨테이너에 객체를 담아 두고, 
		//      필요할 때에 컨테이너로부터 객체를 가져와 사용할 수 있도록 하고 있다. 
		//      스프링은 객체의 생성 및 생명주기를 관리할 수 있는 기능을 제공하고 있으므로, 더이상 개발자에 의해 객체를 생성 및 소멸하도록 하지 않고
		//      객체 생성 및 관리를 스프링 프레임워크가 가지고 있는 객체 관리기능을 사용하므로 Inversion of Control == 제어의 역전 이라고 부른다.  
		//      그래서 스프링 컨테이너를 IoC 컨테이너라고도 부른다.
		
		//  === 느슨한 결합 ===
		//      스프링 컨테이너가 MyBatisTestDAO 클래스 객체에서 SqlSessionTemplate sqlsession 클래스 객체를 사용할 수 있도록 
		//      만들어주는 것을 "느슨한 결합" 이라고 부른다.
		//      느스한 결합은 MyBatisTestDAO 객체가 메모리에서 삭제되더라도 SqlSessionTemplate sqlsession 객체는 메모리에서 동시에 삭제되는 것이 아니라 남아 있다.
	@Autowired
	private SqlSessionTemplate sqlsession;

	@Override
	public int mbtest1() {
		//JDBC설정 Connection 기타등등의 작업이 필요없다.
		
		int n =sqlsession.insert("testdb.mbtest1");
		
		return n;
	}
	@Override
	public int mbtest2(String name) {
		int n = sqlsession.update("examdb.mbtest2",name);
		return n;
	}
	@Override
	public int mbtest3(MybatisTestVO vo) {
		int n = sqlsession.insert("testdb.mbtest3", vo);
		return n;
	}
	@Override
	public int mbtest4(MemberVO mvo) {
		int n = sqlsession.insert("testdb.mbtest4", mvo);
		return n;
	}

	@Override
	public int mbtest5(HashMap<String, String> map) {
		int n = sqlsession.insert("testdb.mbtest5",map);
		return n;
	}
	@Override
	public String mbtest6(int no) {
		String name = sqlsession.selectOne("testdb.mbtest6", no);
		return name;
	}
	@Override
	public MemberVO mbtest7(int no) {
		MemberVO n = sqlsession.selectOne("testdb.mbtest7",no);
		return n;
	}
	@Override
	public List<MemberVO> mbtest8(String addr) {
		List<MemberVO> n = sqlsession.selectList("testdb.mbtest8",addr);
		return n;
	}
	@Override
	public List<MemberVO2> mbtest9(String addr) {
		List<MemberVO2> n = sqlsession.selectList("testdb.mbtest9",addr);
		return n;
	}
	@Override
	public List<MemberVO2> mbtest9_2(String addr) {
		List<MemberVO2> n = sqlsession.selectList("testdb.mbtest9_2",addr);
		return n;
	}
	@Override
	public List<HashMap<String, String>> mbtest10(String addr) {
		List<HashMap<String, String>> n = sqlsession.selectList("testdb.mbtest10",addr);
		return n;
	}
	@Override
	public List<HashMap<String, String>> mbtest11(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> memberList = sqlsession.selectList("testdb.mbtest11",paraMap);
		return memberList;
	}
	@Override
	public List<Integer> mbtest12_deptno() {
		List<Integer> deptnoList = sqlsession.selectList("testdb.mbtest12_deptno");
		return deptnoList;
	}
	@Override
	public List<HashMap<String, String>> mbtest12(HashMap<String, String> paraMap) {
		List<HashMap<String, String>> empList = sqlsession.selectList("testdb.mbtest12", paraMap);
		return empList;
	}
	@Override
	public int ex01(String date) {
		int dday = sqlsession.selectOne("testdb.ex01", date);
		return dday;
	}
	@Override
	public List<HashMap<String, String>> mbtest13(String addrSearch) {
		List<HashMap<String, String>> memberList = sqlsession.selectList("testdb.mbtest13", addrSearch);
		return memberList;
	}
	@Override
	public List<String> ex02() {
		List<String> empList = sqlsession.selectList("testdb.ex02");
		return empList;
	}
	@Override
	public List<HashMap<String, Object>> ex02JSON(String job_id) {
		List<HashMap<String, Object>> empList = sqlsession.selectList("testdb.ex02JSON", job_id);
		return empList;
	}
	@Override
	public List<HashMap<String, Object>> mbtest14(HashMap<String, Object> paraMap) {
		List<HashMap<String, Object>> empList = sqlsession.selectList("testdb.mbtest14", paraMap);
		return empList;
	}
	@Override
	public List<HashMap<String, Object>> mbtest15_gender() {
		List<HashMap<String, Object>> genderList = sqlsession.selectList("testdb.mbtest15_gender");
		return genderList;
	}
	@Override
	public List<HashMap<String, Object>> mbtest15_ageline() {
		List<HashMap<String, Object>> agelineList = sqlsession.selectList("testdb.mbtest15_ageline");
		return agelineList;
	}
	@Override
	public List<HashMap<String, Object>> mbtest15_deptno() {
		List<HashMap<String, Object>> deptnoList = sqlsession.selectList("testdb.mbtest15_deptno");
		return deptnoList;
	}
	@Override
	public List<HashMap<String, Object>> mbtest15_emp(String deptno) {
		List<HashMap<String, Object>> empList = sqlsession.selectList("testdb.mbtest15_emp", deptno);
		return empList;
	}
}
