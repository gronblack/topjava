<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<table class="meals">
    <thead>
        <tr>
            <th class="meals__caption">Date</th>
            <th class="meals__caption">Description</th>
            <th class="meals__caption">Calories</th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${list}" var="mealTo">
            <tr class="meals__row${mealTo.excess ? " meals__row_red" : ""}">
                <td class="meals__cell">${mealTo.dateTime.toString().replaceAll("T", " ")}</td>
                <td class="meals__cell">${mealTo.description}</td>
                <td class="meals__cell">${mealTo.calories}</td>
                <td class="meals__cell"><a href="meals?action=update">Update</a></td>
                <td class="meals__cell"><a href="meals?action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<style>
    .meals {
        border-collapse: collapse;
    }
    .meals__row {
        color: green;
    }
    .meals__row.meals__row_red {
        color: red;
    }
    .meals, .meals__caption, .meals__cell {
        border: 1px solid black;
    }
    .meals__caption, .meals__cell {
        padding: 4px;
    }
</style>
</body>
</html>
