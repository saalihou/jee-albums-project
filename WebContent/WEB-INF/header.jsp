<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<header>
	<h1>Albums</h1>
	<c:if test="${currentUser != null}">
		Bonjour ${currentUser.name} <br />
		<a href="${pageContext.request.contextPath}">Accueil</a>
		<a href="${pageContext.request.contextPath}/albums">Liste des albums</a>
		<a href="${pageContext.request.contextPath}/albums/me">Mes albums</a>
		<a href="${pageContext.request.contextPath}/albums/shared">Albums partages</a>
		<a href="${pageContext.request.contextPath}/users">Liste des utilisateurs</a>
		<a href="${pageContext.request.contextPath}/auth/logout">Deconnexion</a>
	</c:if>
	<c:if test="${currentUser == null}">
		<a href="${pageContext.request.contextPath}/auth/login">Connexion</a>
	</c:if>
</header>
<br />