<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${list}" var="mealTo">
            <tr <c:if test="${mealTo.excess == true}">class="red"</c:if> <c:if test="${mealTo.excess == false}">class="green"</c:if> >
                <td>${fn:replace(mealTo.dateTime, "T", " ")}</td>
                <td><c:out value="${mealTo.description}"/></td>
                <td><c:out value="${mealTo.calories}"/></td>
                <td><a href="meals?action=update">Update</a></td>
                <td><a href="meals?action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<style>
    table, td, th {
        border: 1px solid black;
    }
    table {
        border-collapse: collapse;
    }
    th, td {
        padding: 4px;
    }
    .red {
        color: red;
    }
    .green {
        color: green;
    }
</style>
</body>
</html>
