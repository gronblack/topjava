<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }
        .excess {
            color: red;
        }
        .row {
            display: flex;
        }
        .col {
            display: flex;
            flex-direction: column;
            padding-right: 20px;
        }
        .buttons {
            padding: 15px 0;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form method="get" action="meals" id="filter">
        <input type="hidden" name="action" value="filter">
        <div class="row">
            <div class="col">
                <label for="startDate">От даты (включая)</label>
                <input type="date" name="startDate" id="startDate" autocomplete="off" value="${param.startDate}">
            </div>
            <div class="col">
                <label for="endDate">До даты (включая)</label>
                <input type="date" name="endDate" id="endDate" autocomplete="off" value="${param.endDate}">
            </div>
            <div class="col">
                <label for="startTime">От времени (включая)</label>
                <input type="time" name="startTime" id="startTime" autocomplete="off" value="${param.startTime}">
            </div>
            <div class="col">
                <label for="endTime">До времени (исключая)</label>
                <input type="time" name="endTime" id="endTime" autocomplete="off" value="${param.endTime}">
            </div>
        </div>
        <div class="buttons">
            <button type="submit">Filter</button>
        </div>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>