<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des utilisateurs</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h1>Liste des utilisateurs</h1>
	<a href="${pageContext.request.contextPath}/users/add">Ajouter un utilisateur</a>
	<c:if test="${not empty errorMessage}">
	   <p class="error">${errorMessage}</p>   
	</c:if>
	<br />
	<br />
	<c:if test="${users.size() == 0}">
		<p>Aucun utilisateur dans la base de données</p>
	</c:if>
	<c:if test="${users.size() > 0}">
		<table border="1">
			<thead>
				<tr>
					<th>ID</th>
					<th>Login</th>
					<th>Mot de passe</th>
					<th>Prénoms & Nom</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
				    <tr>      
				        <td>${user.getId()}</td>
				        <td>${user.getLogin()}</td>
				        <td>${user.getPassword()}</td>
				        <td>${user.getName()}</td>
				        <td>
				        	<a href="${pageContext.request.contextPath}/users/edit?id=${user.getId()}">Modifier</a>
				        	<br />
				        	<form onsubmit="confirm('Supprimer cet utilisateur ?') || event.preventDefault()" method="POST" action='${pageContext.request.contextPath}/users/remove?id=${user.getId()}'>
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