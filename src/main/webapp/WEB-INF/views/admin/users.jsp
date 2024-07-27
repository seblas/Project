<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Panel admina</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div style="font-weight: bold; text-align: center">
    <h2>Użytkownicy</h2>
</div>


<table>
    <thead>
    <tr>
        <th>id</th>
        <th>nazwa</th>
        <th>email</th>
        <th>poziom</th>
        <th>rola</th>
        <th>akcja</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.level}</td>
            <c:if test="${user.roles.size()>1}">
                <td>Admin</td>
            </c:if>
            <c:if test="${user.roles.size()==1}">
                <td>User</td>
            </c:if>
            <td>
                <a href="<c:out value="user/delete/${user.id}"/>">usuń</a>
                &nbsp;
                <a href="<c:out value="user/admin/${user.id}"/>">zmień rolę</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>