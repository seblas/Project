<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Logowanie</h3>
<form:form method="post" action="/perform_login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <label>
        <p>Email</p>
        <p><input type="text" name="username"/></p>
    </label>
    <label>
        <p>Hasło</p>
        <p><input type="password" name="password"/></p>
    </label>
    <input type="submit" value="Zaloguj"/>
</form:form>
</body>
</html>