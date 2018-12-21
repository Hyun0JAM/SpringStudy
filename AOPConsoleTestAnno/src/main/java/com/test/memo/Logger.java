package com.test.memo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/* 클래스명에 @Aspect을 붙임으로써 보조업무 클래스인 Logger클래스가 
 * aspect(관점)객체로 등록된다. 
 * 기존의 XML에서 기술된 aop:aspect역할을 한다. */
@Aspect
@Component
public class Logger {
		// === Pointcut 을 생성한다. ===
		/*
		      아래의 메소드가 기존의 XML <aop:pointcut> 역할을 한다.
		   void + 인자값(파라미터) 없음으로 선언한다. 
		     인자값(파라미터)을 선언해도 어차피 어노테이션의  AspectJ 표현식이 우선이기 때문에 
		     메소드 자체의 형식은 의미없다.
		     또한 구현부도 의미없다.
		     대신에 메소드 이름이 나중에 각각의 Advice에서 식별자로 사용된다.      
		 */
		@Pointcut("execution(public * com.test.memo.Memo.*(..))")
		public void m1() {
			
		}
		
		//보조업무
		// - 메모를 작성하는데 걸리는 시간을 로그에 남기는 보조업무
		// 보조업무 생성과 동시에 Advice 설정도 같이 선언한다.
		// 아래의 @Around 어노테이션이 기존의 XML <aop:around> 역할을 한다.
		/*@Around("m1()")
		public void arround(ProceedingJoinPoint  joinPoint) throws Throwable {
			
			//보조업무 시작
			long start = System.currentTimeMillis();
			System.out.println("시간 기록 시작");
			
			try {
				// 주업무 시작
				// - joinPoint.proceed(); 메소드를 실행함으로 
				//   Pointcut 에 설정된 주업무를 처리해주는 메소드가 호출되어 실행된다.
				//   지금은 public * com.test.memo.Memo.*(..) 이다. 
				
				joinPoint.proceed();  
				
				//   그리로 joinPoint.proceed() 메소드의 리턴값은 Object 이다.;
				//   이를통해 Aspect 로 연결된 Original Method(지금은 Memo 클래스의 모든 메소드임) 의 
				//   리턴값을 형변환을 통하여 받을수 있다.
				//   지금은 Original Method 의 리턴값이 void 임.
				// 주업무 끝
				
				//=> 주업무와 보조업무가 묶여 있다. ->AOP
			} finally {
				
				long finish = System.currentTimeMillis();
				System.out.println("시간 기록 끝");
				System.out.println("주업무 실행시간 : " + (finish - start) + "ms");
				//보조업무 끝
				
			}

		}*/
		
		// 보조업무 추가
		// - 메모를 작성하기 전 현재 시간을 로그에 남기는 보조업무
		// 보조업무 생성과 동시에 Advice 설정도 같이 선언한다.
		// 아래의 @Before 어노테이션이 기존의 XML <aop:Before> 역할을 한다.
		@Before("m1()")
		public void before() {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String now = sdf.format(new Date());
			
			System.out.println("   메모 작성 날짜시간 : " + now);
			
			//System.out.printf("메모 작성 시간 : %tT\n", Calendar.getInstance());
			
		}
		
		// 보조업무 추가
		// - 메모를 작성, 수정하면 메모 작성 횟수와 메모 수정 횟수를 누적해서 로그에 남기는 업무.
		// 보조업무 생성과 동시에 Advice 설정도 같이 선언한다.
		// 아래의 @Before 어노테이션이 기존의 XML <aop:Before> 역할을 한다.
		int cnt =0;
		@After("m1()")
		public void after() {
			cnt++;
			System.out.printf("==> 메모 작성 또는 수정횟수 : %d회\n",cnt);
			
		}
		
		// 보조업무 추가
		// - 메모를 삭제한 뒤 삭제한 메모의 번호를 로그에 남기는 보조업무
		// -!!중요!!- 보조 객체의 인자명(파라미터명)과 xml의 Returning 의 인자명(파라미터명)은 동일 해야한다. !!
		// 보조업무 생성과 동시에 Advice 설정도 같이 선언한다.
		// 아래의 @Before 어노테이션이 기존의 XML <aop:Before> 역할을 한다.
		@AfterReturning(pointcut="m1()",returning="seq") // pointcut을 안쓰면 기본이 pointcut으로 인식됨
		public void afterreturning(Object seq) {
			System.out.println("삭제된 메모번호 : "+seq);
		}
		

		// 보조업무 추가
		// 메모를 읽다가 예외가 발생하면 로그를 남기는 보조업무
		// -!!중요!!- 보조 객체의 인자명(파라미터명)과 xml의 Returning 의 인자명(파라미터명)은 동일 해야한다. !!
		// 보조업무 생성과 동시에 Advice 설정도 같이 선언한다.
		// 아래의 @afterthrowing 어노테이션이 기존의 XML <aop:after-throwing> 역할을 한다.
		@AfterThrowing(pointcut="m1()",throwing="e")
		public void afterthrowing(Exception e) {
			System.out.println("예외기록 : "+e.getMessage());
		}
}
