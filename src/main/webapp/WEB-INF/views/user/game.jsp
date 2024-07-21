<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Dodaj grę</title>
    <style>
        span[id$=".errors"] {
            color: red;
        }
    </style>
</head>
<jsp:include page="header.jsp"/>


<div style="font-weight: bold; text-align: center">
    <h2>Dodawanie gry</h2>
</div>
<form:form method="post" modelAttribute="game">
    <label>
        <p>Czas rozpoczęcia i zakończenia gry<sup>*</sup></p>
        <p><form:input path="startTime" value="${game.startTime}" type="datetime-local" required="required"/> - <form:input path="endTime"
                                                                                                        value="${game.endTime}"
                                                                                                        type="datetime-local" required="required"/>
            <br>
            <form:errors path="startTime"/> <form:errors path="endTime"/>
        </p>
    </label>
    <label>
        <p>Termin zgłaszania<sup>*</sup></p>
        <p><form:input path="recruitmentEndTime" value="${game.recruitmentEndTime}" type="datetime-local" required="required"/>
            <br>
            <form:errors path="recruitmentEndTime"/>
        </p>
    </label>
    <label>
        <p>Wybierz boisko<sup>*</sup></p>
        <p><form:select path="field" items="${fields}" itemValue="id" itemLabel="address.cityStreetAndNumber"/>
            <form:errors path="field"/></p>
    </label>
    <label>
        <p>Koszt gry<sup>*</sup></p>
        <p><form:input path="cost" value="${game.cost}" type="number" min="0" required="required"/>
            <br>
            <form:errors path="cost"/>
        </p>
    </label>
    <label>
        <p>Poziom graczy (od - do)<sup>*</sup></p>
        <p><form:select path="minLevel" items="${levels}" value="${game.minLevel}"/> - <form:select path="maxLevel"
                                                                                                    items="${levels}"
                                                                                                    value="${maxLevel}"/>
            <br>
            <form:errors path="minLevel"/> <form:errors path="maxLevel"/>
        </p>
    </label>
    <label>
        <p>Wiek graczy (od - do)<sup>*</sup></p>
        <p><form:input path="minAge" value="${game.minAge}" type="number" min="16" max="60" required="required"/> - <form:input path="maxAge" value="${game.maxAge}" type="number" min="16" max="60" required="required"/>
            <br>
            <form:errors path="minAge"/> <form:errors path="maxAge"/>
        </p>
    </label>
    <label>
        <p>Opis</p>
        <p><form:textarea path="description" value="${game.description}" cols="30" rows="6"/>
            <br>
            <form:errors path="description"/>
        </p>
    </label>


    <input type="submit" value="Zapisz"/>
</form:form>
<p><sup>*</sup> - pole wymagane</p>
</body>
</html>