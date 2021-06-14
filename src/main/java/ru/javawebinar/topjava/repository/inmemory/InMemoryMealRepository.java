package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> meal2User = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    {
        MealsUtil.mealsAdmin.forEach(meal -> save(meal, SecurityUtil.authUserId()));
        MealsUtil.mealsUser.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            meal2User.put(meal.getId(), userId);
            log.info("save {}", meal);
            return meal;
        }
        log.info("save {}", meal);
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> checkedMeal2User(meal, userId));
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {}", id);
        return checkedMeal2User(repository.get(id), userId) != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal {}", id);
        return checkedMeal2User(repository.get(id), userId);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAll meals");
        Predicate<Meal> filter = meal -> checkedMeal2User(meal, userId) != null;
        filter = filter.and(DateTimeUtil.getDateFilter(startDate, endDate));

        return repository.values().stream().filter(filter)
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    private Meal checkedMeal2User(Meal meal, int userId) {
        return meal2User.get(meal.getId()) == userId ? meal : null;
    }
}

