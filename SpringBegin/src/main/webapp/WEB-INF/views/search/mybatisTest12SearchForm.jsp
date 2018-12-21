<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String ctxPath = request.getContextPath(); %>
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
	
	.total {background-color: #ffff99; 
	        font-weight: bold;
	        text-align: center;}
</style>
<script type="text/javascript" src="<%=ctxPath %>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#gender").val("${gender}");
    	$("#department_id").val("${department_id}");
    	
    });// end of $(document).ready()---------------------

    
    function searchKeep() {
    	
    }// end of function searchKeep()---------------------
    
    
	function goSearch() {
		var frm = document.searchFrm;
		frm.method="GET";
		frm.action="<%=ctxPath %>/mybatistest/mybatisTest12.action";
		frm.submit();
		
	}// end of function goSearch()------------------
</script>	
</head>
<body>
	<div align="center">
		<h2>우리회사 사원명단</h2>
		<br/>
		<form name="searchFrm">
			<select name="department_id" id="department_id">
				<option value="">모든부서</option>
				<c:forEach items="${deptnoList }" var="deptno">
					<c:if test="${deptno<0 }">
						<option value="${deptno }">부서없음</option>
					</c:if>
					<option value="${deptno }">${deptno }</option>
				</c:forEach>
			</select>
			<select name="gender" id="gender">
				<option value="전체">전체</option>
				<option value="남">남</option>
				<option value="여">여</option>
			</select>
			<button type="button" onClick="goSearch();">검색</button>&nbsp;&nbsp;
			<button type="button" onClick="javascript:location.href='mybatisTest13.action'">초기화</button>
		</form>
	</div>
	<c:if test="${empList != null}">
		<div align="center" style="margin-top: 50px;">
		  <table>
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
			<tbody>
				<c:forEach items="${empList }" var="emp" varStatus="status">
				<tr>
					<td>${status.count }</td>
					<td>${emp.DEPARTMENT_ID }</td>
					<td>${emp.DEPARTMENT_NAME }</td>
					<td>${emp.EMPLOYEE_ID }</td>
					<td>${emp.NAME }</td>
					<td>${emp.JUBUN }</td>
					<td>${emp.GENDER }</td>
					<td>${emp.AGE }</td>
					<td><fmt:formatNumber value="${emp.YEARPAY }" pattern="###,###" />$</td>
				</tr>
				</c:forEach>
				<c:if test="${empty empList }">
					<tr><td colspan="9" style="text-align:center;">검색 결과가 존재하지 않습니다.</td></tr>
				</c:if>
				<tr><td colspan="9" style="text-align:right;">검색결과 : ${empList.size() } 개</td></tr>
			</tbody>
		  </table>
		</div>
	</c:if>
</body>
</html>
