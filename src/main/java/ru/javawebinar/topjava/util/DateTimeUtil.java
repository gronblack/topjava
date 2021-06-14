package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static boolean isBetweenHalfOpen(LocalDate lt, LocalDate start, LocalDate end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) <= 0;
    }

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime start, LocalTime end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static Predicate<Meal> getDateFilter(LocalDate startDate, LocalDate endDate) {
        Predicate<Meal> filter = meal -> true;

        if (startDate != null || endDate != null) {
            LocalDate finalStartDate = startDate == null ? LocalDate.MIN : startDate;
            LocalDate finalEndDate = endDate == null ? LocalDate.MAX : endDate;
            filter = filter.and(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), finalStartDate, finalEndDate));
        }

        return filter;
    }

    public static Predicate<MealTo> getTimeFilter(LocalTime startTime, LocalTime endTime) {
        Predicate<MealTo> filter = mealTo -> true;

        if (startTime != null || endTime != null) {
            LocalTime finalStartTime = startTime == null ? LocalTime.MIN : startTime;
            LocalTime finalEndTime = endTime == null ? LocalTime.MAX : endTime;
            filter = filter.and(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), finalStartTime, finalEndTime));
        }

        return filter;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}