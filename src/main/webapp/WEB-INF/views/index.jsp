<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Elastic Search</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/index.css" />">
</head>
<body>
	<a href="${contextPath}/getuploadpage">Upload Data</a>
	<h2>Elastic Search!</h2>

	<div class="content-search-part">
		<form action="${contextPath}/search" method="get" onsubmit="aaa();">
			<input type="search" value="" name="keysearch" id="keysearch"/>
			<input type="hidden" value="" name="keyword" id="keyword"/>
			<input type="submit" value="Search" id="search" />
		</form>
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
