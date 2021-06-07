<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<form class="mealForm" action="meals" method="post">
    <p class="mealForm__row">
        <label class="mealForm__label" for="datetime">DateTime:</label>
        <input class="mealForm__input" id="datetime" name="datetime" type="datetime-local" value="${meal.dateTime}">
    </p>
    <p class="mealForm__row">
        <label class="mealForm__label" for="description">Description:</label>
        <input class="mealForm__input" id="description" name="description" type="text" value="${meal.description}">
    </p>
    <p class="mealForm__row">
        <label class="mealForm__label" for="calories">Calories:</label>
        <input class="mealForm__input" id="calories" name="calories" type="number" value="${meal.calories}">
    </p>
    <input name="id" type="hidden" value="${meal.id}">
    <p class="mealForm__row mealForm__row_buttons">
        <input type="submit">
        <input type="reset">
    </p>
</form>

<style>
    .mealForm {
        display: inline-block;
    }
    .mealForm__row {
        display: flex;
        justify-content: space-between;
    }
    .mealForm__row_buttons {
        justify-content: flex-start;
    }
    .mealForm__row_buttons input {
        margin-right: 10px;
    }
    .mealForm__label {
        min-width: 100px;
    }
    .mealForm__input {
        width: 100%;
    }
</style>
</body>
</html>
