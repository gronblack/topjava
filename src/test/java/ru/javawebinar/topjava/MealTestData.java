package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final LocalDate DATE_START = LocalDate.of(2020, 1, 30);
    public static final LocalDate DATE_END = LocalDate.of(2020, 1, 31);
    public static final List<Meal> userMeals;
    public static final List<Meal> userMealsOneDate;
    public static final List<Meal> userMealsBetween;

    public static final Meal userMeal100002 = new Meal(START_SEQ + 2, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак чемпиона", 400);
    public static final Meal userMeal100003 = new Meal(START_SEQ + 3, LocalDateTime.of(2020, 1, 30, 13, 0), "Котлетки", 1000);
    public static final Meal userMeal100004 = new Meal(START_SEQ + 4, LocalDateTime.of(2020, 1, 30, 20, 0), "Салатик", 500);
    public static final Meal userMeal100005 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, 1, 31, 0, 0), "Студень Загадочный", 200);
    public static final Meal userMeal100006 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, 1, 31, 19, 30), "Много всего", 1000);
    public static final Meal userMeal100007 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, 1, 31, 13, 25), "Обед, да", 500);
    public static final Meal userMeal100008 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин из пива", 410);
    public static final Meal userMeal100009 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, 2, 1, 20, 0), "Ужин юзера 1 февраля", 510);
    public static final Meal userMeal100010 = new Meal(START_SEQ + 10, LocalDateTime.of(2020, 2, 1, 10, 25), "Завтрак юзера 1 февраля", 650);

    public static final Meal adminMeal100011 = new Meal(START_SEQ + 11, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак админа", 500);

    static {
        userMeals = Arrays.asList(userMeal100009, userMeal100010, userMeal100008, userMeal100006, userMeal100007, userMeal100005, userMeal100004, userMeal100003, userMeal100002);
        userMealsOneDate = Arrays.asList(userMeal100004, userMeal100003, userMeal100002);
        userMealsBetween = Arrays.asList(userMeal100008, userMeal100006, userMeal100007, userMeal100005, userMeal100004, userMeal100003, userMeal100002);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertIsEmpty(Iterable<Meal> actual) {
        assertThat(actual).isEmpty();
    }

    public static Meal getUpdated(Meal m) {
        Meal updated = new Meal(m);
        updated.setDateTime(LocalDateTime.of(2021, 7, 31, 20, 45));
        updated.setDescription("Updated meal");
        updated.setCalories(9999);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, 5, 30, 12, 35, 0), "New meal", 7777);
    }
}