<%@ page import="static ru.javawebinar.topjava.web.SecurityUtil.authUserId" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h3>Проект <a href="https://github.com/JavaWebinar/topjava" target="_blank">Java Enterprise (Topjava)</a></h3>
<hr>
<form action="users" method="post" id="form">
    <p>
        <label for="userId">User:</label>
        <select name="userId" id="userId" onchange="document.querySelector('#form').submit()">
            <option value="1" <%= authUserId() == 1 ? "selected" : "" %>>Admin</option>
            <option value="2" <%= authUserId() == 2 ? "selected" : "" %>>Regular User</option>
        </select>
    </p>
    <ul style="font-size: large">
        <li><a href="users">Users</a></li>
        <li><a href="meals">Meals</a></li>
    </ul>
</form>
</body>
</html>
