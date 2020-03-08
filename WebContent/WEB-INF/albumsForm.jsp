<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulaire Album</title>
<style>
.error {
	color: red;
}
.thumbnail-container {
	width: 300px;
	height: 300px;
	display: inline-block;
	text-align: center;
}

.thumbnail {
	width: 100%;
	height: 100%;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form method="POST">
		<c:if test="${not empty successMessage}">
		   <p>${successMessage}</p>   
		</c:if>
		<c:if test="${not empty errorMessage}">
		   <p class="error">${errorMessage}</p>   
		</c:if>
		<c:forEach items="${errorMessages}" var="errorMessage">
		   <p class="error">${errorMessage}</p>   
		</c:forEach>
		<a href="${pageContext.request.contextPath}/albums">Retourner a la liste</a>
		<br />
		<br />
		<fieldset>
			<legend>Informations album</legend>
			<label>Nom</label>
			<input ${readonly ? "readonly='true'" : ""} name="name" type="text" value='${album != null ? album.getName() : ""}' autofocus />
			<br />
			<c:if test="${not readonly}">
				<label>Public</label>
				<input ${readonly ? "readonly='true'" : ""} name="isPublic" type="checkbox" ${album != null && album.isPublic() ? "checked='checked'" : ""} />
				<br />
				<input type="submit" value="Ajouter" />
			</c:if>
		</fieldset>
	</form>
	<div>
		<c:forEach items="${images}" var="image">
			<div class="thumbnail-container">
				<img class="thumbnail" src="${pageContext.request.contextPath}/images/${image.getPath()}" alt="${image.getDescription()} }" />
				<h5>${image.getTitle()}</h5>
			</div>
		</c:forEach>
	</div>
	<c:if test="${not readonly && album != null}">
		<form method="POST" action="${pageContext.request.contextPath}/images/upload" enctype="multipart/form-data">
			<fieldset>
				<legend>Ajouter une image</legend>
				<label>Image</label>
				<input name="image" type="file" />
				<br />
				<input name="title" type="text" />
				<br />
				<textarea name="description"></textarea>
				<br />
				<input name="keywords" type="text" />
				<br />
				<input name="albumId" value="${album != null ? album.getId() : null}" type="hidden" />
				<br />
				<input type="submit" value="Ajouter" />
			</fieldset>
		</form>
	</c:if>
</body>
</html>