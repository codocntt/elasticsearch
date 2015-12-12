<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/upload.css" />">
<title>Elastic Search</title>
</head>
<body>
	<h1>Upload Data</h1>
	<div class="main">
		<form action="${contextPath}/upload" method="post" enctype="multipart/form-data">
			<span>Title:</span>
			<input type="text" name="title"/>
			<br/>
			<span>Author:</span>
			<input type="text" name="author"/>
			<br/>
			<span>Des:</span>
			<textarea rows="4" cols="50" name="description"></textarea>
			<br/>
			<span>File:</span>
			<input type="file" name="file" id="upload" /> 
			<br />
			<div class="control-butotn">
				<input type="submit" value="UPLOAD" id="upload" />
				<input type="button" value="CANCEL" id="cancel" />
			</div>
		</form>
	</div>
</body>
</html>