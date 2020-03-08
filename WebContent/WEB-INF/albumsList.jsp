<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste de mes albums</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h1>Liste de mes albums</h1>
	<a href="${pageContext.request.contextPath}/albums/add">Ajouter un album</a>
	<c:if test="${not empty errorMessage}">
	   <p class="error">${errorMessage}</p>   
	</c:if>
	<br />
	<br />
	<c:if test="${albums.size() == 0}">
		<p>Aucun album ajouté</p>
	</c:if>
	<c:if test="${albums.size() > 0}">
		<table border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nom</th>
					<th>Public</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${albums}" var="album">
				    <tr>      
				        <td>${album.getId()}</td>
				        <td>${album.getName()}</td>
				        <td>${album.isPublic() ? 'Public' : 'Privé'}</td>
				        <td>
				        	<a href="${pageContext.request.contextPath}/albums/edit?id=${album.getId()}">Voir</a>
				        	<br />
				        	<form onsubmit="confirm('Supprimer cet album ?') || event.preventDefault()" method="POST" action='${pageContext.request.contextPath}/albums/remove?id=${album.getId()}'>
				        		<input type="submit" value="Supprimer" />
				        	</form>
				        </td>
				    </tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</body>
</html>