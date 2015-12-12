<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Elastic Search</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/response.css" />">
</head>
<body>
	<div class="main-content">
		<div class="content-search-part">
			<form action="${contextPath}/search" method="get" onsubmit="aaa();">
			<input type="search" value="${keyword}" name="keysearch" id="keysearch"/>
			<input type="hidden" value="" name="keyword" id="keyword"/>
			<input type="submit" value="Search" id="search" />
		</form>
		</div>
		<hr>
		<div class="content">
			<c:if test="${not empty list}">
				<c:forEach items="${list}" var="e">
					<span >${e.title}</span><br/>
					${e.date} Author : ${e.author}
					<br/>
					Highlight : ${e.highlight}
					<br/><a href="#">Download</a>
					<br/><br/>
				</c:forEach>
			</c:if>
			<c:if test="${empty list}">
			No result.
			</c:if>
		</div>
	</div>
<script src="${contextPath}/resources/js/1.7.2.jquery.min.js"></script>
<script type="text/javascript">

 	function aaa(){
 		var value = $("#keysearch").val();
	 	value = encodeURI(value);
	 	$("#keyword").val(value);
 	}
	
</script>	
</body>
</html>
