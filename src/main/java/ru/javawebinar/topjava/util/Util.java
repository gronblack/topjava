package ru.javawebinar.topjava.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.Nullable;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static <T> T unproxyIfNeed(T o) {
        return o instanceof HibernateProxy ? (T) Hibernate.unproxy(o) : o;
    }
}