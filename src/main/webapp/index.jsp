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
        <input type="hidden" name="userId" value="${userId}">
        <label for="userIdSelect">User:</label>
        <select name="userIdSelect" id="userIdSelect" onchange="document.querySelector('#form').submit()">
            <option value="1" ${userId == 1 ? "selected" : ""}>Admin</option>
            <option value="2" ${userId == 2 ? "selected" : ""}>Regular User</option>
        </select>
    </p>
    <ul style="font-size: large">
        <li><a href="users">Users</a></li>
        <li><a href="meals">Meals</a></li>
    </ul>
</form>
${userId == null ? "<script>document.addEventListener('DOMContentLoaded', e => {document.querySelector('#form').submit();});</script>" : ""}
</body>
</html>
