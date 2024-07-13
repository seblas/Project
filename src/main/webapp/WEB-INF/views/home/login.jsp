<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Logowanie</h3>
<form:form method="post" action="/perform_login" modelAttribute="user">
    <label>
        <p>Email</p>
        <p><form:input path="email" value="${user.email}"/></p>
    </label>
    <label>
        <p>Hasło</p>
        <p><form:password path="password"/></p>
    </label>
    <input type="submit" value="Zaloguj"/>
</form:form>
</body>
</html>