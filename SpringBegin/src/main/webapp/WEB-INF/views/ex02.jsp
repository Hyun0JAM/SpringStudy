<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
<script>
	$(document).ready(function(){
		$("#job_id").change(function(){
			var form_data = {"job_id":$(this).val()};
			$.ajax({
				url:"ex02JSON.action",
				type:"GET",
				data:form_data,
				dataType:"json",
				success:function(json){
					var html = "";
					if(json.length>0){
						$.each(json,function(entryIndex,entry){
							html += "<tr><td>"+(entryIndex+1)+"</td>"
							+"<td>"+entry.DEPARTMENT_ID+"</td>"
							+"<td>"+entry.EMPLOYEE_ID+"</td>"
							+"<td>"+entry.NAME+"</td>"
							+"<td>"+entry.JUBUN+"</td>"
							+"<td>"+entry.GENDER+"</td>"
							+"<td>"+entry.AGE+"</td>"
							+"<td>"+entry.YEARPAY+"</td>"
							+"</tr>";
						});
					}
					else{
						html += "<tr><td colspan='8'>검색된 결과가 없습니다.</td></tr>";
					}
					$("#result").empty().html(html);
				},
				error: function(){}
			});
		});
	});
</script>
<style>
	td{
		border: 1px solid lightgray;
	}
</style>
</head>
<body>
	<div align="center">
		<h2>직업별 연봉</h2>
		<select id="job_id" name="job_id">
			<option value="">모든직업</option>
			<c:forEach items="${jobList }" var="job">
				<option value="${job }">${job }</option>
			</c:forEach>
		</select>
	</div>
	<table id="emptbl" align="center" style="margin-top:2%;width:1000px;text-align:center; border-collapse: collapse;">
		<thead>
			<tr>
				<td>일련번호</td>
				<td>부서번호</td>
				<td>사원번호</td>
				<td>사원명</td>
				<td>주민번호</td>
				<td>성별</td>
				<td>나이</td>
				<td>연봉</td>
			</tr>
		</thead>
		<tbody id="result">
		</tbody>
	</table>
</body>
</html>