package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static List<Meal> userMeals;
    public static List<Meal> adminMeals;
    public static final LocalDate DATE_BETWEEN = LocalDate.parse("2020-01-30");
    public static List<Meal> userMealsBetween;
    public static List<Meal> adminMealsBetween;

    static {
        int seq = Math.max(ADMIN_ID, USER_ID);
        int seqBetween = seq;
        userMealsBetween = Stream.of(
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак чемпиона", 400),
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T13:00:00"), "Котлетки", 1000),
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T20:00:00"), "Салатик", 500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
        seqBetween += 4;
        adminMealsBetween = Stream.of(
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак админа", 500),
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед админа", 1000),
                new Meal(++seqBetween, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин админа", 500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

        userMeals = Stream.of(
                new Meal(++seq, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак чемпиона", 400),
                new Meal(++seq, LocalDateTime.parse("2020-01-30T13:00:00"), "Котлетки", 1000),
                new Meal(++seq, LocalDateTime.parse("2020-01-30T20:00:00"), "Салатик", 500),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T00:00:00"), "Студень Загадочный", 200),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T19:30:00"), "Много всего", 1000),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T13:25:00"), "Обед, да", 500),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T20:00:00"), "Ужин из пива", 410))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
        adminMeals = Stream.of(
                new Meal(++seq, LocalDateTime.parse("2020-01-30T10:00:00"), "Завтрак админа", 500),
                new Meal(++seq, LocalDateTime.parse("2020-01-30T13:00:00"), "Обед админа", 1000),
                new Meal(++seq, LocalDateTime.parse("2020-01-30T20:00:00"), "Ужин админа", 500),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T00:00:00"), "Еда на граничное значение админа", 100),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T10:00:00"), "Завтрак админа 2", 1000),
                new Meal(++seq, LocalDateTime.parse("2020-01-31T20:00:00"), "Ужин админа 2", 410))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertNotMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isNotEqualTo(expected);
    }

    public static Meal getUpdated(List<Meal> list, int id) {
        Meal updated = new Meal( getMealById(list, id) );
        updated.setDescription("Updated meal");
        updated.setCalories(9999);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.parse("2021-05-30T12:35:00"), "New meal", 7777);
    }

    public static int getAnyId(List<Meal> list) {
        return list.stream()
                .mapToInt(AbstractBaseEntity::getId)
                .findAny()
                .orElse(NOT_FOUND);
    }

    public static Meal getMealById(List<Meal> list, int id) {
        return list.stream()
                .filter(m -> m.getId() == id)
                .findAny()
                .orElse(null);
    }
}