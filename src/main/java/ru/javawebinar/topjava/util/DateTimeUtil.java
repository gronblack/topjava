package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime start, LocalTime end) {
        return lt.compareTo(start) >= 0 && lt.compareTo(end) < 0;
    }

    public static Predicate<Meal> getDateFilter(LocalDate startDate, LocalDate endDate) {
        Predicate<Meal> filter = meal -> true;

        if (startDate != null)
            filter = filter.and(meal -> meal.getDate().compareTo(startDate) >= 0);
        if (endDate != null)
            filter = filter.and(meal -> meal.getDate().compareTo(endDate) <= 0);

        return filter;
    }

    public static Predicate<Meal> getTimeFilter(LocalTime startTime, LocalTime endTime) {
        Predicate<Meal> filter = meal -> true;

        if (startTime != null)
            filter = filter.and(meal -> meal.getTime().compareTo(startTime) >= 0);
        if (endTime != null)
            filter = filter.and(meal -> meal.getTime().compareTo(endTime) < 0);

        return filter;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}