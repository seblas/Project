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

<c:if test="${not empty message}">
    <div style="text-align: center; color: red">
        ${message}
    </div>
</c:if>


<div style="text-align: center">
    <a href="<c:out value="/user/game"/>">Szukaj graczy (dodaj nową grę)</a>
</div>
<br>
<div>
    <p>Twoje gry</p>
    <table>
        <thead>
        <tr>
            <th>id</th>
            <th>boisko</th>
            <th>czas gry</th>
            <th>termin zgłoszeń</th>
            <th>status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${gamesToJSP}" var="game">
            <tr>
                <c:forEach items="${game}" var="detail">
                    <td>${detail}</td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>