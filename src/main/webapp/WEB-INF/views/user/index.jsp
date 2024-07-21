<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Panel użytkownika</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div style="font-weight: bold; text-align: center">
    <h2>Panel użytkownika</h2>
</div>

<div style="text-align: center">
    <a href="<c:out value="/user/game"/>">Szukaj graczy (dodaj nową grę)</a>
</div>
</body>
</html>