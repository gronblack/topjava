package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final LocalDate DATE_START = LocalDate.of(2020, 1, 30);
    public static final LocalDate DATE_END = LocalDate.of(2020, 1, 31);
    public static final List<Meal> userMeals;
    public static final List<Meal> adminMeals;
    public static final List<Meal> userMealsOneDate;
    public static final List<Meal> adminMealsOneDate;

    public static final Meal meal100002 = new Meal(100002, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак чемпиона", 400);
    public static final Meal meal100003 = new Meal(100003, LocalDateTime.of(2020, 1, 30, 13, 0), "Котлетки", 1000);
    public static final Meal meal100004 = new Meal(100004, LocalDateTime.of(2020, 1, 30, 20, 0), "Салатик", 500);
    public static final Meal meal100005 = new Meal(100005, LocalDateTime.of(2020, 1, 31, 0, 0), "Студень Загадочный", 200);
    public static final Meal meal100006 = new Meal(100006, LocalDateTime.of(2020, 1, 31, 19, 30), "Много всего", 1000);
    public static final Meal meal100007 = new Meal(100007, LocalDateTime.of(2020, 1, 31, 13, 25), "Обед, да", 500);
    public static final Meal meal100008 = new Meal(100008, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин из пива", 410);
    public static final Meal meal100009 = new Meal(100009, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак админа", 500);
    public static final Meal meal100010 = new Meal(100010, LocalDateTime.of(2020, 1, 30, 13, 0), "Обед админа", 1000);
    public static final Meal meal100011 = new Meal(100011, LocalDateTime.of(2020, 1, 30, 20, 0), "Ужин админа", 500);
    public static final Meal meal100012 = new Meal(100012, LocalDateTime.of(2020, 1, 31, 0, 0), "Еда на граничное значение админа", 100);
    public static final Meal meal100013 = new Meal(100013, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак админа 2", 1000);
    public static final Meal meal100014 = new Meal(100014, LocalDateTime.of(2020, 1, 31, 20, 0), "Ужин админа 2", 410);

    static {
        userMeals = new ArrayList<>();
        userMeals.add(meal100008);
        userMeals.add(meal100006);
        userMeals.add(meal100007);
        userMeals.add(meal100005);
        userMeals.add(meal100004);
        userMeals.add(meal100003);
        userMeals.add(meal100002);

        userMealsOneDate = new ArrayList<>();
        userMealsOneDate.add(meal100004);
        userMealsOneDate.add(meal100003);
        userMealsOneDate.add(meal100002);

        adminMeals = new ArrayList<>();
        adminMeals.add(meal100014);
        adminMeals.add(meal100013);
        adminMeals.add(meal100012);
        adminMeals.add(meal100011);
        adminMeals.add(meal100010);
        adminMeals.add(meal100009);

        adminMealsOneDate = new ArrayList<>();
        adminMealsOneDate.add(meal100011);
        adminMealsOneDate.add(meal100010);
        adminMealsOneDate.add(meal100009);
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