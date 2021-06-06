package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Singleton
public class MealDaoRam implements Dao<Meal, Integer> {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static ConcurrentMap<Integer, Meal> meals;

    private static class MealDaoRamHolder {
        private static final MealDaoRam HOLDER_INSTANCE = new MealDaoRam();
    }

    private MealDaoRam() {}

    public static MealDaoRam getInstance() {
        return MealDaoRamHolder.HOLDER_INSTANCE;
    }

    @Override
    public List<Meal> getAll() {
        // init
        if (meals == null) {
            meals = Stream.of(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
            ).collect(Collectors.toConcurrentMap(
                    meal -> {
                        meal.setId(idCounter.addAndGet(1));
                        return meal.getId();
                    },
                    meal -> meal));
        }

        return meals.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Meal meal) {
        meal.setId(idCounter.addAndGet(1));
        meals.putIfAbsent(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        return meals.get(id);
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
    }

    @Override
    public void update(Integer id, Meal meal) {
        meal.setId(id);
        meals.put(id, meal);
    }
}
