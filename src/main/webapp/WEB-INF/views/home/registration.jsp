<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Rejestracja</title>
    <style>
        span[id$=".errors"] {
            color: red;
        }
    </style>
</head>
<body>
<h3>Rejestracja</h3>
<form:form method="post"  modelAttribute="user">
    <label>
        <p>Nazwa<sup>*</sup></p>
        <p><form:input path="name" value="${user.name}" name="name" required="required"/> <form:errors path="name"/></p>
    </label>
    <label>
        <p>Email<sup>*</sup></p>
        <p><form:input path="email" value="${user.email}" name="email" required="required"/> <form:errors path="email"/></p>
    </label>
    <label>
        <p>Data urodzenia<sup>*</sup></p>
        <p><form:input path="dateOfBirth" value="${user.dateOfBirth}" type="date" name="date" required="required"/> <form:errors
                path="dateOfBirth"/></p>
    </label>
    <label>
        <p>Poziom gry<sup>*</sup></p>
        <p><form:select path="level" items="${levels}"/> <form:errors path="level"/></p>
    </label>
    <label>
        <p>Kod i miejscowość<sup>**</sup></p>
        <p><form:input path="address.code" value="${user.address.code}" maxlength="6" minlength="6" size="6" name="code"/>
            <form:input path="address.city" value="${user.address.city}" name="city"/> <form:errors path="address.code"/>
            <form:errors path="address.city"/></p>
        <p> Ulica i numer domu</p>
        <p><form:input path="address.street" value="${user.address.street}" name="street"/>
            <form:input path="address.number" value="${user.address.number}" size="5" name="number"/> <form:errors path="address.street"/>
            <form:errors path="address.number"/></p>
    </label>
    <label>
        <p>Hasło<sup>*</sup></p>
        <p><form:password path="password" required="required"/>
            <br>
            <form:errors path="password"/></p>
    </label>
    <label>
        <p>Powtórz hasło<sup>*</sup></p>
        <p><form:password path="confirmPassword" required="required"/> <form:errors path="confirmPassword"/></p>
    </label>

    <input type="submit" value="Zapisz"/>
</form:form>
<p><sup>*</sup> - pole wymagane</p>
<p><sup>**</sup> - adres zapisze się tylko wtedy kiedy wypełnisz wszystkie pola</p>
</body>
</html>