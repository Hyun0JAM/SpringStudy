<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function goEX(){
		var frm = document.dateFrm;
		frm.method="GET";
		frm.action="<%=request.getContextPath()%>/ex01.action";
		frm.submit();
	}
</script>
</head>
<body>
	<form name="dateFrm">
		<input type="date" id="date" name="date" value="${date }" /><button type="button" id="btn" onClick="goEX();">계산하기</button><c:if test="${dday!=null }">D${dday }</c:if>
	</form>
</body>
</html>