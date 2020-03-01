<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<form method="POST">
		<c:if test="${not empty successMessage}">
		   <p>${successMessage}</p>   
		</c:if>
		<c:if test="${not empty errorMessage}">
		   <p class="error">${errorMessage}</p>   
		</c:if>
		<br />
		<br />
		<fieldset>
			<legend>Connexion</legend>
			<label>Login</label>
			<input name="login" type="text" value='${user != null ? user.getLogin() : ""}' autofocus />
			<br />
			<label>Mot de passe</label>
			<input name="password" type="password" value='${user != null ? user.getPassword() : ""}' />
			<br />
			<input type="submit" value="Ajouter" />
		</fieldset>
	</form>
</body>
</html>