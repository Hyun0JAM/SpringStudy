<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<% 
   String ctxPath = request.getContextPath(); 
   //  /startspring 임.
%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	table {border: 1px solid gray;
	       border-collapse: collapse;
	      }
	
	th, td {border: 1px solid gray;}
    
	select.my_select {height: 25px;}  	
</style>

<script type="text/javascript" src="<%=ctxPath %>/resources/js/jquery-3.3.1.min.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
    	callAjax("",$("#gender").val(),$("#ageline").val());
    	$("#btnSearch").click(function(){
    		var deptnoArr = new Array();
    		$(".department_id").each(function(){
    			var bool = $(this).is(":checked"); // 체크박스의 체크유무검사
    			if(bool) deptnoArr.push($(this).val());
    		});
    		var deptnos = deptnoArr.join();
    		//console.log(deptnos);
    		var gender = $("#gender").val();
    		var ageline = $("#ageline").val();
    		console.log(gender,ageline);
    		callAjax(deptnos,gender,ageline);
    	});
    }); // end of $(document).ready()--------------------------------------------

	function callAjax(deptnoesStr, gender, ageline) {
		var form_data = {"deptnoesStr":deptnoesStr,"gender":gender,"ageline":ageline};
    	$.ajax({
			url:"mybatisTest14JSON.action",
			type:"GET",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				var html = "";
				$.each(json,function(entryIndex,entry){
					html += "<tr><td>"+(entryIndex+1)+"</td>"
						+"<td>"+entry.DEPARTMENT_ID+"</td>"
						+"<td>"+entry.DEPARTMENT_NAME+"</td>"
						+"<td>"+entry.EMPLOYEE_ID+"</td>"
						+"<td>"+entry.NAME+"</td>"
						+"<td>"+entry.JUBUN+"</td>"
						+"<td>"+entry.GENDER+"</td>"
						+"<td>"+entry.AGE+"</td>"
						+"<td>"+entry.YEARPAY+"</td></tr>";
				});
				$("#result").html(html);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});    	
    }// end of function callAjax(addrSearch)------------
    
</script>	

</head>
<body>

	<div align="center">
		<h2>우리회사 사원명단</h2>
		<br/>
		
		<form name="searchFrm">
			<c:forEach items="${deptnoList }" var="deptno">
				<c:if test="${deptno < 0 }">
					<input type="checkbox" name="department_id" class="department_id" id="department_id${deptno }" value=""><label for="department_id${deptno }">부서없음</label>
				</c:if>
				<c:if test="${deptno>0 }">
					<input type="checkbox" name="department_id" class="department_id" id="department_id${deptno }" value="${deptno }" /><label for="department_id${deptno }">${deptno }번 부서</label>
				</c:if>
			</c:forEach>
			<br/><br/>
			
			<select name="gender" id="gender" class="my_select">
				<option value="">성별</option>
				<option value="남">남</option>
				<option value="여">여</option>
			</select>
						
			<select name="ageline" id="ageline" class="my_select">
				<option value="">연령대</option>
				<option value="0">10대미만</option>
				<option value="10">10대</option>
				<option value="20">20대</option>
				<option value="30">30대</option>
				<option value="40">40대</option>
				<option value="50">50대</option>
				<option value="60">60대</option>
			</select>
			
			<button type="button" id="btnSearch">검색</button>&nbsp;&nbsp;
		</form>
	</div>
	
	<div id="display" align="center" style="margin-top: 50px;">
		<table style="width: 900px;">
			<thead>
				<tr>
					<th>일련번호</th>
					<th>부서번호</th>
					<th>부서명</th>
					<th>사원번호</th>
					<th>사원명</th>
					<th>주민번호</th>
					<th>성별</th>
					<th>나이</th>
					<th>연봉</th>
				</tr>
			</thead>
			
			<tbody id="result"></tbody>
		</table>
	</div>
	
</body>
</html>
