<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String ctxPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=ctxPath%>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#display").hide();
		$("#addrSearch").keydown(function(event){ //엔터를 했을경우
			if(event.keyCode == 13){
				var addrSearch = $(this).val();
				callAjax(addrSearch);
				return false;
				/* JSP 페이지에서 Enter 이벤트 발생시 JSP 페이지에 input type의 text 박스가 오로지 하나인 경우에는 엔터를 치면 입력한 글자가 사라진다.
		  		     그래서 입력한 글자가 사라지지 않게 하려면  맨끝에 return false; 를 적어주면 사라지지 않는다.*/
			}
		});
		$("#btnSearch").click(function(){
			var addrSearch = $("#addrSearch").val();
			callAjax(addrSearch);
		});
		$("#btnClear").click(function(){
			location.reload();
			$("#addSearch").focus();
		});
	});
	function callAjax(addrSearch){
		var form_data = {"addrSearch":addrSearch};
		$.ajax({
			url:"mybatisTest13JSON.action",
			type:"GET",
			data:form_data,
			dataType:"JSON",
			success:function(json){
				$("#display").show();
				var html = "";
				if(json.length>0){
					$.each(json,function(entryIndex,entry){
						html += "<tr><td>"+(entryIndex+1)+"</td>"
								+"<td>"+entry.NAME+"</td>"
								+"<td>"+entry.EMAIL+"</td>"
								+"<td>"+entry.TEL+"</td>"
								+"<td>"+entry.ADDR+"</td>"
								+"<td>"+entry.WRITEDAY+"</td>"
								+"<td>"+entry.BIRTHDAY+"</td></tr>";
					});
				}
				else{
					html += "<tr><td colspan='8' style='color:red'>검색된 데이터가 없습니다.</td></tr>";
				}
				$("#result").empty().html(html);
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
			}
		});
	}
	function goSearch() {
		var frm = document.searchFrm;
		frm.method = "GET";
		frm.action = "<%=ctxPath %>/mybatistest/mybatisTest8End.action"; 
		frm.submit();
	}
</script>	
</head>
<body>
	<div align="center">
		<h2>주소를 입력받아 해당주소지에 거주하는 회원들을 찾아주는 페이지(Ajax JSONObject, JSONArray 사용)(/mybatistest/mybatisTest13.action)</h2>
		<form name="searchFrm">
			주소 : <input type="text" id="addrSearch" /> <%-- input type="text"가  --%> 
			<br/><br/>
			<button type="button" id="btnSearch">찾기</button>
			<button type="button" id="btnClear">초기화</button>
		</form>
	</div>
	<div id="display" align="center" style="margin-top: 50px;">
		<table>
			<thead>
				<tr>
					<th>회원번호</th>
					<th>성명</th>
					<th>이메일</th>
					<th>전화</th>
					<th>주소</th>
					<th>가입일자</th>
					<th>생일</th>
				</tr>
			</thead>
			<tbody id="result">
			</tbody>
		</table>
	</div>
</body>
</html>
