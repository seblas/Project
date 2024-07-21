<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Logowanie</title>
</head>
<body>
<c:if test="${not empty registrationSuccessMessage}">
    <h4>${registrationSuccessMessage}</h4>
</c:if>
<h3>Logowanie</h3>
<c:if test="${param.error != null}">
    <div style="color: red;">Zły email lub hasło!</div>
</c:if>
<form:form method="post" action="/perform_login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <label>
        <p>Email</p>
        <p><input type="text" name="username" required="required"/></p>
    </label>
    <label>
        <p>Hasło</p>
        <p><input type="password" name="password" required="required"/></p>
    </label>
    <input type="submit" value="Zaloguj"/>
</form:form>
<div>
    <a href="<c:out value="/registration"/>">Załóż nowe konto</a>
</div>
</body>
</html>