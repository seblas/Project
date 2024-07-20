<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Panel użytkownika</title>
</head>
<body>
<div style="text-align: right">
    <span style="font-weight: bold">
        ${user.name}&nbsp;
    </span>
    (<sec:authentication property="principal.username"/>)
    <br>
    <sec:authorize access="hasRole('ADMIN')">
        <a href="<c:out value="/admin"/>">Panel admina</a>
        <br>
    </sec:authorize>
    <form action="<c:url value="/perform_logout"/>" method="post">
        <input type="submit" value="Wyloguj" style="background: none; border: none; color: blue; text-decoration: underline; cursor: pointer;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<div style="height: 50px">
    <hr>
</div>

<div style="font-weight: bold; text-align: center">
    <h2>Panel użytkownika<h2>
</div>

<div style="text-align: center">
    <a href="<c:out value="/user/game"/>">Szukaj graczy (dodaj nową grę)</a>
</div>
</body>
</html>