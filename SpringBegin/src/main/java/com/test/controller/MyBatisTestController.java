package com.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.test.model.MemberVO;
import com.test.model.MemberVO2;
import com.test.model.MybatisTestVO;
import com.test.service.MybatisTestService;

/*
사용자 웹브라우저 요청(View)  ==> DispatcherServlet ==> @Controller 클래스 <==>> Service단(핵심업무로직단, business logic단) <==>> Model단[Repository](DAO, DTO) <==>> myBatis <==>> DB(오라클)           
(http://...  *.action)                                  |
       ↑                                              |
       |                                              ↓
       |                                           View단(.jsp)
       -----------------------------------------------| 
                                                       
Service(서비스)단 객체를 업무 로직단(비지니스 로직단)이라고 부른다.
Service(서비스)단 객체가 하는 일은 Model단에서 작성된 데이터베이스 관련 여러 메소드들 중 관련있는것들만을 모아 모아서
하나의 트랜잭션 처리 작업이 이루어지도록 만들어주는 객체이다.
여기서 업무라는 것은 데이터베이스와 관련된 처리 업무를 말하는 것으로 Model 단에서 작성된 메소드를 말하는 것이다.
이 서비스 객체는 @Controller 단에서 넘겨받은 어떤 값을 가지고 Model 단에서 작성된 여러 메소드를 호출하여 실행되어지도록 해주는 것이다.
실행되어진 결과값을 @Controller 단으로 넘겨준다.
*/

@Controller
/* XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 
     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다.
     여기서는 @Controller 를 사용하므로 @Component 기능이 이미 있으므로 @Component를 명기하지 않아도 MyBatisTestController 는 bean 으로 등록되어 스프링컨테이너가 자동적으로 관리해준다.  */
public class MyBatisTestController {
	// ※ 의존객체주입(DI : Dependency Injection) 
		//  ==> 스프링 프레임워크는 객체를 관리해주는 컨테이너를 제공해주고 있다.
		//      스프링 컨테이너는 bean으로 등록되어진 MyBatisTestController 클래스 객체가 사용되어질때, 
		//      MyBatisTestController 클래스의 인스턴스 객체변수(의존객체)인 MyBatisTestService service 에 
		//      자동적으로 bean 으로 등록되어 생성되어진 MyBatisTestService service 객체를  
		//      MyBatisTestController 클래스의 인스턴스 변수 객체로 사용되어지게끔 넣어주는 것을 의존객체주입(DI : Dependency Injection)이라고 부른다. 
		//      이것이 바로 IoC(Inversion of Control == 제어의 역전) 인 것이다.
		//      즉, 개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것에서 탈피하여 스프링은 컨테이너에 객체를 담아 두고, 
		//      필요할 때에 컨테이너로부터 객체를 가져와 사용할 수 있도록 하고 있다. 
		//      스프링은 객체의 생성 및 생명주기를 관리할 수 있는 기능을 제공하고 있으므로, 더이상 개발자에 의해 객체를 생성 및 소멸하도록 하지 않고
		//      객체 생성 및 관리를 스프링 프레임워크가 가지고 있는 객체 관리기능을 사용하므로 Inversion of Control == 제어의 역전 이라고 부른다.  
		//      그래서 스프링 컨테이너를 IoC 컨테이너라고도 부른다.
		
		//  === 느슨한 결합 ===
		//      스프링 컨테이너가 MyBatisTestController 클래스 객체에서 MyBatisTestService 클래스 객체를 사용할 수 있도록 
		//      만들어주는 것을 "느슨한 결합" 이라고 부른다.
		//      느스한 결합은 MyBatisTestController 객체가 메모리에서 삭제되더라도 MyBatisTestService service 객체는 메모리에서 동시에 삭제되는 것이 아니라 남아 있다.

	@Autowired
	private MybatisTestService service;
	// private MyBatisTestService service = new MyBatisTestService();
		// === 단단한 결합(개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것) ===
		//    ==> MyBatisTestController 객체가 메모리에서 삭제 되어지면 MyBatisTestService service 객체는 멤버변수이므로 메모리에서 자동적으로 삭제되어진다.
	
	@RequestMapping(value="/mybatistest/mybatisTest1.action",method={RequestMethod.GET})
	public String MybatisTest1(HttpServletRequest req){
		int n = service.mbtest1();
		
		String msg="";
		if(n==1) msg="데이터 입력 성공";
		else msg="데이터 입력 실패";
		req.setAttribute("msg", msg);
		
		return "mybatisTest1";
	}
	
	@RequestMapping(value="/mybatistest/mybatisTest2.action",method={RequestMethod.GET})
	public String MybatisTest2(HttpServletRequest req){
		
		String name= "엄정화";
		
		int n = service.mbtest2(name);
		
		String msg="";
		if(n==1) msg = n+"개의 데이터 입력 성공";
		else msg = "데이터 입력 실패";
		req.setAttribute("msg", msg);
		
		return "mybatisTest2";
	}
	// form을 띄워주는 페이지는 대부분 GET방식이다.
	// 하지만, 파일을 추가하는 form은 POST방식이다.
	@RequestMapping(value="/mybatistest/mybatisTest3.action",method={RequestMethod.GET})
	public String MybatisTest3(){
		
		return "register/mybatisTest3AddForm";
	}
	@RequestMapping(value="/mybatistest/mybatisTest3End.action",method={RequestMethod.POST})
	public String MybatisTest3End(HttpServletRequest req){
		
		// 1.form에서 넘어온 값 받기
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String tel=req.getParameter("tel");
		String addr=req.getParameter("addr");
		
		// 2. DTO(VO)에 넣어준다.
		MybatisTestVO vo = new MybatisTestVO();
		
		vo.setName(name);
		vo.setEmail(email);
		vo.setTel(tel);
		vo.setAddr(addr);
		
		// 3.service단으로 생성된 DTO(VO)를 넘긴다.
		int n = service.mbtest3(vo);
		String msg="";
		if(n==1) msg="회원가입 성공";
		else msg="회원가입 실패";
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest3AddEnd";
	}
	
	@RequestMapping(value="/mybatistest/mybatisTest4.action",method={RequestMethod.GET})
	public String MybatisTest4(){
		
		return "register/mybatisTest4AddForm";
	}
	@RequestMapping(value="/mybatistest/mybatisTest4End.action",method={RequestMethod.POST})
	public String MybatisTest4End(MemberVO mvo,HttpServletRequest req){
		
		// **** form 에서 넘어오는 name 의 값과 
		//      DB 의 컬럼명과 DTO(VO)의 get과 set다음에 나오는 메소드명(첫글자는 대문자)이
		//      동일할 경우 위처럼 파라미터명에 MemberVO mvo 와 같이 넣어주기만 하면
		//      form에 입력된 값들이 자동적으로  MemberVO mvo 에 입력되어지므로  		
		
		// 1. form 에서 넘어온 값 받기위하여 
		//    사용하였던 req.getParameter("name"); 이러한 작업이 
		//    필요없다.
				
		// 2. DTO(VO)에 넣어주는 작업도 필요없다.
		int n = service.mbtest4(mvo);
		String msg="";
		if(n==1) msg="회원가입 성공";
		else msg="회원가입 실패";
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest4AddEnd";
	}
	@RequestMapping(value="/mybatistest/mybatisTest5.action",method={RequestMethod.GET})
	public String MybatisTest5(){
		
		return "register/mybatisTest5AddForm";
	}
	@RequestMapping(value="/mybatistest/mybatisTest5End.action",method={RequestMethod.POST})
	public String MybatisTest5End(HttpServletRequest req){
		
		// 1.form에서 넘어온 값 받기
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String tel=req.getParameter("tel");
		String addr=req.getParameter("addr");
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("email", email);
		map.put("tel", tel);
		map.put("addr", addr);
		
		// 2. DTO(VO)에 넣어주는 작업도 필요없다.
		int n = service.mbtest5(map);
		String msg="";
		if(n==1) msg="회원가입 성공";
		else msg="회원가입 실패";
		req.setAttribute("msg", msg);
		
		return "register/mybatisTest5AddEnd";
	}
	
	// HttpServletRequest사용예제
	@RequestMapping(value="/mybatistest/mybatisTest6.action",method={RequestMethod.GET})
	public String MybatisTest6(){
		// 검색 조건에 맞는 데이터를 찾기위해 Search하는 form페이지를 띄운다.
		return "search/mybatisTest6SearchForm";
	}
	@RequestMapping(value="/mybatistest/mybatisTest6End.action",method={RequestMethod.POST})
	public String MybatisTest6End(HttpServletRequest req){
		String str_no = "";
		String result = "";
		try {
			str_no = req.getParameter("no");
			int no = Integer.parseInt(str_no);
			
			// 2.Service단으로 검색어를 넘긴다.
			String name = service.mbtest6(no);
			if(name != null) {
				result = name;
			}
			else result = "검색하시려는 "+no+"는 존재하지 않습니다.";
		} catch(NumberFormatException e) {
			req.setAttribute("str_no", str_no);
			return "search/mybatisTest6SearchError";
		}
		req.setAttribute("result", result);
		return "search/mybatisTest6SearchEnd";
	}
	
	// HttpServletRequest사용예제
	@RequestMapping(value="/mybatistest/mybatisTest6_2.action",method={RequestMethod.GET})
	public String MybatisTest6_2(){
		// 검색 조건에 맞는 데이터를 찾기위해 Search하는 form페이지를 띄운다.
		return "search/mybatisTest6_2SearchForm";
	}
	@RequestMapping(value="/mybatistest/mybatisTest6_2End.action",method={RequestMethod.POST})
	public String MybatisTest6_2End(HttpServletRequest req, Model model){ // Model : db에서 넘어온 결과물을 담는 객체
		String str_no = "";
		String result = "";
		try {
			str_no = req.getParameter("no");
			int no = Integer.parseInt(str_no);
			
			// 2.Service단으로 검색어를 넘긴다.
			String name = service.mbtest6(no);
			if(name != null) {
				result = name;
			}
			else result = "검색하시려는 "+no+"는 존재하지 않습니다.";
			//Model 객체응 이용해서 view단으로 Data전달하기
			model.addAttribute("result",result);
			return "search/mybatisTest6_2SearchEnd";
		} catch(NumberFormatException e) {
			model.addAttribute("str_no", str_no);
			return "search/mybatisTest6_2SearchError";
		}
	}  
    // === *** ModelAndView 사용 *** === 
    @RequestMapping(value="/mybatistest/mybatisTest6_3.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest6_3() {
         // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.    
         return "search/mybatisTest6SearchForm_3";// View 단
         // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }  
    @RequestMapping(value="/mybatistest/mybatisTest6End_3.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public ModelAndView mybatisTest6End_3(HttpServletRequest req, ModelAndView mv) {      
         String str_no = "";
         try {
            str_no = req.getParameter("no");
            int no = Integer.parseInt(str_no);
            //2. Service 단으로 검색어를 넘긴다.
            String name = service.mbtest6(no);
            String result = null;
            if(name != null) {
               result  = name;
            }else {
               result = "검색하시려는 번호 "+no+"에 일치하는 데이터가 없습니다.";               
            }
            // ModelAndView 객체를 사용하여 데이터와 뷰를 동시에 설정이 가능하다.
            mv.addObject("result",result);
            // 모든 객체를 담을수가 있다. mv.addObject(키값,) 
            // 1. 뷰로 보낼 데이터 값
            mv.setViewName("search/mybatisTest6SearchEnd_3"); // 뷰단을 지정해준다
            // 2. 뷰의 이름을 지정한다.
         } catch (NumberFormatException e) {
            mv.addObject("str_no",str_no);
            mv.setViewName("search/mybatisTest6SearchError_3");
            // 뷰로 보낼 데이터 값
         }   
         return mv;
    }
    ///////////////////////////////////////////////////////////////////////////////////
    // 한행 가져오기   
    @RequestMapping(value="/mybatistest/mybatisTest7.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest7() {
       // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
       return "search/mybatisTest7SearchForm";// View 단
       // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }        
    @RequestMapping(value="/mybatistest/mybatisTest7End.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest7End(HttpServletRequest req) {  
       String str_no = "";
       try {
          str_no = req.getParameter("no");
          int no = Integer.parseInt(str_no);
          //2. Service 단으로 검색어를 넘긴다.
          MemberVO mvo = service.mbtest7(no);
          req.setAttribute("mvo", mvo);
          req.setAttribute("no", no);
          
          return "search/mybatisTest7SearchEnd";
       } catch (NumberFormatException e) {
          req.setAttribute("str_no", str_no);
          return "search/mybatisTest7SearchError";
       }   
    }    
    /////////////////////////////////////////////////////////////////////////////////    
    // === *** ModelAndView 사용 *** === 
    @RequestMapping(value="/mybatistest/mybatisTest7_2.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public ModelAndView mybatisTest7_2(ModelAndView mav) {
         // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
         mav.setViewName("search/mybatisTest7SearchForm_2");// View 단
         // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
         return mav;
    }  
    @RequestMapping(value="/mybatistest/mybatisTest7End_2.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public ModelAndView mybatisTest7End_2(HttpServletRequest req,ModelAndView mav) {  
         String str_no = "";
         try {
            str_no = req.getParameter("no");
            int no = Integer.parseInt(str_no);
            //2. Service 단으로 검색어를 넘긴다.
            MemberVO mvo = service.mbtest7(no);
            mav.addObject("mvo",mvo);
            mav.addObject("no",no);
            mav.setViewName("search/mybatisTest7SearchEnd_2");  
         } catch (NumberFormatException e) {
            mav.addObject("str_no", str_no);
            mav.setViewName("search/mybatisTest7SearchError_2");
         }   
         return mav;
    }                  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 다중 행 리스트 가져오기
    @RequestMapping(value="/mybatistest/mybatisTest8.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest8() {
        // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
        return "search/mybatisTest8SearchForm";// View 단
        // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }
    @RequestMapping(value="/mybatistest/mybatisTest8End.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest8End(HttpServletRequest req) {   
         String addr = req.getParameter("addr");                        
         //2. Service 단으로 검색어를 넘긴다.
         List<MemberVO> memberList = service.mbtest8(addr);
         req.setAttribute("memberList", memberList);
         req.setAttribute("addr", addr);
         return "search/mybatisTest8SearchEnd";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // 컬럼명과 VO의 이름이 다른 경우
    @RequestMapping(value="/mybatistest/mybatisTest9.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest9() {
       // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
       return "search/mybatisTest9SearchForm";// View 단
       // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }
    @RequestMapping(value="/mybatistest/mybatisTest9End.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest9End(HttpServletRequest req) {  
         String addr = req.getParameter("addr");                        
         //2. Service 단으로 검색어를 넘긴다.
         List<MemberVO2> memberList = service.mbtest9(addr);
         req.setAttribute("memberList", memberList);
         req.setAttribute("addr", addr);
         return "search/mybatisTest9SearchEnd";
    }       
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // 컬럼명과 VO의 이름이 다른 경우
    @RequestMapping(value="/mybatistest/mybatisTest9_2.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest9_2() {
       // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
       return "search/mybatisTest9SearchForm_2";// View 단
       // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }  
    @RequestMapping(value="/mybatistest/mybatisTest9End_2.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest9End_2(HttpServletRequest req) {   
         String addr = req.getParameter("addr");                        
         //2. Service 단으로 검색어를 넘긴다.
         List<MemberVO2> memberList = service.mbtest9_2(addr);
         req.setAttribute("memberList", memberList);
         req.setAttribute("addr", addr);
         return "search/mybatisTest9SearchEnd_2";
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    // 컬럼명과 VO의 이름이 다른 경우
    @RequestMapping(value="/mybatistest/mybatisTest10.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest10() {
       // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
       return "search/mybatisTest10SearchForm";// View 단
       // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }   
    @RequestMapping(value="/mybatistest/mybatisTest10End.action",method= {RequestMethod.GET}) //url=/mybatistest/mybatisTest1.cation
    public String mybatisTest10End(HttpServletRequest req) {  
      String addr = req.getParameter("addr");                        
         //2. Service 단으로 검색어를 넘긴다.
         List<HashMap<String,String>> memberMapList = service.mbtest10(addr);
         req.setAttribute("memberMapList", memberMapList);
         req.setAttribute("addr", addr);
         return "search/mybatisTest10SearchEnd";
	}
    @RequestMapping(value="/mybatistest/mybatisTest11.action",method= {RequestMethod.GET})
    public String mybatisTest11(HttpServletRequest req) {
    	String colName = req.getParameter("colName");
	    String searchWord = req.getParameter("searchWord");
    	String startDay = req.getParameter("startday");
    	String endDay = req.getParameter("endday");
	    if(searchWord !=null && !searchWord.trim().isEmpty()) {
	    	HashMap<String,String> paraMap = new HashMap<String,String>();
	    	paraMap.put("COLNAME", colName);
	    	paraMap.put("SEARCHWORD", searchWord);
	    	paraMap.put("STARTDAY", startDay);
	    	paraMap.put("ENDDAT", endDay);
	    	
	    	List<HashMap<String,String>> memberList = service.mbtest11(paraMap);
	    	req.setAttribute("memberList", memberList);
	    	req.setAttribute("searchWord", searchWord);
	    	req.setAttribute("colName", colName);
	    	req.setAttribute("startDay", startDay);
	    	req.setAttribute("endDay", endDay);
	    }
        // 검색조건에 맞는 데이트럴 찾기위해 Search 하는 form 페이지를 띄운다.
        return "search/mybatisTest11SearchForm";// View 단
        // /WEB-INF/views/register/mybatisTest6SearchForm.jsp
    }
    @RequestMapping(value="/mybatistest/mybatisTest12.action",method= {RequestMethod.GET})
    public String mybatisTest12(HttpServletRequest req) {
    	String department = req.getParameter("department_id");
    	String gender = req.getParameter("gender");
    	List<Integer> deptnoList = service.mbtest12_deptno();
    	HashMap<String,String> paraMap = new HashMap<String,String>();
    	paraMap.put("DEPARTMENT_ID", department);
    	paraMap.put("GENDER", gender);
    	List<HashMap<String,String>> empList = service.mbtest12(paraMap);
    	req.setAttribute("empList", empList);
    	req.setAttribute("deptnoList", deptnoList);
    	req.setAttribute("department_id", department);
    	req.setAttribute("gender", gender);
        return "search/mybatisTest12SearchForm";
    }
    @RequestMapping(value="/mybatistest/mybatisTest12_2.action",method= {RequestMethod.GET})
    public ModelAndView mybatisTest12_2(HttpServletRequest req,ModelAndView mav) {
    	String department = req.getParameter("department_id");
    	String gender = req.getParameter("gender");
    	List<Integer> deptnoList = service.mbtest12_deptno();
    	HashMap<String,String> paraMap = new HashMap<String,String>();
    	paraMap.put("DEPARTMENT_ID", department);
    	paraMap.put("GENDER", gender);
    	List<HashMap<String,String>> empList = service.mbtest12(paraMap);
    	mav.addObject("empList", empList);
    	mav.addObject("deptnoList", deptnoList);
    	mav.addObject("department_id", department);
    	mav.addObject("gender", gender);
    	mav.setViewName("search/mybatisTest12SearchForm_2");
        return mav;
    }
    /////////========AJAX========///////////////
    @RequestMapping(value="/mybatistest/mybatisTest13.action",method= {RequestMethod.GET})
    public String mtbatistest13() {
    	return "search/mybatisTest13SearchForm";
    }
    @RequestMapping(value="/mybatistest/mybatisTest13JSON.action",method= {RequestMethod.GET})
    public String mtbatistest13JSON(HttpServletRequest req) {
    	String addrSearch = req.getParameter("addrSearch");
    	JSONArray jsonarr = new JSONArray();
    	if(addrSearch != null && !addrSearch.trim().isEmpty()) {
    		List<HashMap<String,String>> memberList = service.mbtest13(addrSearch);
    		if(memberList !=null && memberList.size() > 0) {
    			for(HashMap<String,String> member : memberList) {
    				JSONObject jobj = new JSONObject();
    				jobj.put("NO", member.get("NO"));
    				jobj.put("NAME", member.get("NAME"));
    				jobj.put("EMAIL", member.get("EMAIL"));
    				jobj.put("TEL", member.get("TEL"));
    				jobj.put("ADDR", member.get("ADDR"));
    				jobj.put("WRITEDAY", member.get("WRITEDAY"));
    				jsonarr.put(jobj);
    			}
    		}
    		String str_jsonarr = jsonarr.toString();
    		req.setAttribute("str_jsonarr", str_jsonarr);
    	}
    	return "search/mybatisTest13JSON";
    }
    /////////========AJAX========///////////////
    @RequestMapping(value="/mybatistest/mybatisTest13_2.action",method= {RequestMethod.GET})
    public String mtbatistest13_2() {
    	return "search/mybatisTest13SearchForm_2";
    }
    @RequestMapping(value="/mybatistest/mybatisTest13JSON_2.action",method= {RequestMethod.GET})
    @ResponseBody
    // ==> jackson JSON 라이브러리와 @ResponseBody 사용하여 JSON 을 파싱하기 === //
 	/* @ResponseBody 란?
 	     메소드에 @ResponseBody Annotation이 되어 있으면 return 되는 값은 View 단을 통해서 출력되는 것이 아니라 
 	   HTTP Response Body에 바로 직접 쓰여지게 된다. 
 		
    !!! 그리고 jackson JSON 라이브러리를 사용할때 주의해야할 점은!!!!! 
 	     메소드의 리턴타입은 행이 1개 일경우                          HashMap<K,V>       이거나            Map<K,V>   이고 
 		                         행이 2개 이상일 경우           List<HashMap<K,V>>      이거나   List<Map<K,V>>  이어야 한다.
 		                         그런데 행이 2개 이상일 경우  ArrayList<HashMap<K,V>> 이거나   ArrayList<Map<K,V>> 이면 안된다.!!!
 	     
 	     이와같이 jackson JSON 라이브러리를 사용할때의 장점은 View 단이 필요없게 되므로 간단하게 작성하는 장점이 있다. */	
    public List<HashMap<String,Object>> mtbatistest13JSON_2(HttpServletRequest req) {
    	String addrSearch = req.getParameter("addrSearch");
    	List<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
    	if(addrSearch != null && !addrSearch.trim().isEmpty()) {
    		List<HashMap<String,String>> memberList = service.mbtest13(addrSearch);
    		if(memberList !=null && memberList.size() > 0) {
    			for(HashMap<String,String> member : memberList) {
    				HashMap<String,Object> memberMap = new HashMap<String,Object>();
    				memberMap.put("NO", member.get("NO"));
    				memberMap.put("NAME", member.get("NAME"));
    				memberMap.put("EMAIL", member.get("EMAIL"));
    				memberMap.put("TEL", member.get("TEL"));
    				memberMap.put("ADDR", member.get("ADDR"));
    				memberMap.put("WRITEDAY", member.get("WRITEDAY"));
    				mapList.add(memberMap);
    			}
    		}
    	}
    	return mapList;
    }
    // ===== testdb.xml파일에 foreach를 사용하는 예제 =====//
    @RequestMapping(value="/mybatistest/mybatisTest14.action",method= {RequestMethod.GET})
    public String mtbatistest14(HttpServletRequest req) {
    	List<Integer> deptnoList = service.mbtest12_deptno();
    	req.setAttribute("deptnoList", deptnoList);
    	return "search/mybatisTest14SearchForm";
    }
    @ResponseBody
    @RequestMapping(value="/mybatistest/mybatisTest14JSON.action",method= {RequestMethod.GET})
    public List<HashMap<String,Object>> mtbatistest14JSON(HttpServletRequest req) {
    	String deptnoesStr = req.getParameter("deptnoesStr");
    	String gender = req.getParameter("gender");
    	String ageline = req.getParameter("ageline");
    	System.out.println(deptnoesStr+gender+ageline);
    	String[] deptnoArr = deptnoesStr.split(",");
    	HashMap<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("DEPTNOARR", deptnoArr);
    	paraMap.put("GENDER", gender);
    	paraMap.put("AGELINE", ageline);
    	
    	List<HashMap<String,Object>> resultList = service.mbtest14(paraMap);
    	return resultList;
    }
    // === 차트그리기 ===
    @RequestMapping(value="/mybatistest/mybatisTest15.action",method= {RequestMethod.GET})
    public String mtbatistest15() {
    	return "chart/mybatisTest15";
    }
    @ResponseBody
    @RequestMapping(value="/mybatistest/mybatisTest15JSON_gender.action",method= {RequestMethod.GET})
    public List<HashMap<String,Object>> mtbatistest15JSON_gender(HttpServletRequest req) {
    	List<HashMap<String,Object>> genderList = service.mbtest15_gender();
    	return genderList;
    }
    @ResponseBody
    @RequestMapping(value="/mybatistest/mybatisTest15JSON_ageline.action",method= {RequestMethod.GET})
    public List<HashMap<String,Object>> mtbatistest15JSON_ageline(HttpServletRequest req) {
    	List<HashMap<String,Object>> agelineList = service.mbtest15_ageline();
    	return agelineList;
    }
    @ResponseBody
    @RequestMapping(value="/mybatistest/mybatisTest15JSON_deptno.action",method= {RequestMethod.GET})
    public List<HashMap<String,Object>> mtbatistest15JSON_deptno(HttpServletRequest req) {
    	List<HashMap<String,Object>> deptnoList = service.mbtest15_deptno();
    	for(int i=0;i<deptnoList.size();i++) {
    		List<HashMap<String,Object>> empList = service.mbtest15_emp((String)deptnoList.get(i).get("DEPTNO"));
    		deptnoList.get(i).put("empList", empList);
    	}
    	return deptnoList;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    // 연습문제1. 날짜 계산하기 or 요일 계산하기
    @RequestMapping(value="/ex01.action",method= {RequestMethod.GET})
    public String es01(HttpServletRequest req) {
    	String date = req.getParameter("date");
    	if(date!=null && !"".equals(date)) {
	        int dday = service.ex01(date);
	        req.setAttribute("dday", dday);
    	}
    	return "ex01";
    }
    @RequestMapping(value="/ex02.action",method= {RequestMethod.GET})
    public String es02(HttpServletRequest req) {
    	List<String> jobList = service.ex02();
    	req.setAttribute("jobList", jobList);
    	return "ex02";
    }
    @ResponseBody
    @RequestMapping(value="/ex02JSON.action",method= {RequestMethod.GET})
    public List<HashMap<String,Object>> ex02JSON(HttpServletRequest req){
    	String job_id = req.getParameter("job_id");
    	System.out.println(job_id);
    	List<HashMap<String,Object>> empList = service.ex02JSON(job_id);	
		return empList;
	}
}
