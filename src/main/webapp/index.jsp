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
    <label for="userId">User:</label>
    <select name="userId" id="userId">
        <option value="1">Admin</option>
        <option value="2">Regular User</option>
    </select>
    <input type="submit">
</form>
<ul style="font-size: large">
    <li><a href="users">Users</a></li>
    <li><a href="meals">Meals</a></li>
</ul>
</body>
</html>
