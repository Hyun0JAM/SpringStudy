<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
    	$("#searchWord").val("${searchWord}");
    	$("#colName").val("${colName}");
    	$("#startday").val("${startDay}");
    	$("#endday").val("${endDay}");
    });// end of $(document).ready()---------------------
	function searchKeep() {	    	
    }// end of function searchKeep()---------------------
	function goSearch() {	
		var searchWord = $("#searchWord").val().trim();
    	if(searchWord == ""){
    		alert("검색어는 한글자 이상 되어야합니다.");
    		return;
    	}
    	else{
    		var frm = document.searchFrm;
    		frm.method="GET";
    		frm.action="<%=ctxPath%>/mybatistest/mybatisTest11.action";
    		frm.submit();
    	}
	}// end of function goSearch()------------------
</script>	
</head>
<body>
	<div align="center">
		<h2>회원명단</h2>
		<br/>
		<form name="searchFrm">
			<select name="colName" id="colName">
				<option value="name">성명</option>
				<option value="email">이메일</option>
				<option value="tel">전화</option>
				<option value="addr">주소</option>
			</select>
			<input type="text" name="searchWord" id="searchWord" size="20" />
			<br/><br/>
			시작일 : <input type="date" name="startday" class="datepicker" id="startday" size="12" /> ~ 종료일 : <input type="date" name="endday" class="datepicker" id="endday" size="12" /> 
			<br/><br/>
			<button type="button" onClick="goSearch();">검색</button>&nbsp;&nbsp;
			<button type="button" onClick="javascript:location.href='mybatisTest12.action'">초기화</button>
		</form>
	</div>
	<c:if test="${memberList != null}">
		<div align="center" style="margin-top: 50px;">
		  <table>
			<thead>
				<tr>
				    <th>일련번호</th>
					<th>회원번호</th>
					<th>성명</th>
					<th>이메일</th>
					<th>전화번호</th>
					<th>주소</th>
					<th>가입일자</th>
					<th>생일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${memberList }" var="member" varStatus="status">
				<tr>
					<td>${status.count }</td>
					<td>${member.NO }</td>
					<td>${member.NAME }</td>
					<td>${member.EMAIL }</td>
					<td>${member.TEL }</td>
					<td>${member.ADDR }</td>
					<td>${member.WRITEDAY }</td>
					<td>${member.BIRTHDAY }</td>
				</tr>
				</c:forEach>			
				<c:if test="${empty memberList}">
				  	<tr><td colspan="8" style="text-align:center;">"${searchWord }"에 해당하는 결과가 없습니다.</td></tr>
				</c:if>
			</tbody>
		  </table>
		  <p>검색결과 : ${memberList.size() } 개</p>
		</div>
		</c:if>
</body>
</html>
