package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {
    public List<MealTo> getAll() {
        return MealsUtil.getTos(super.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(Map<String, Temporal> dtParams) {
        Predicate<MealTo> filter = DateTimeUtil.getTimeFilter(
                (LocalTime) dtParams.getOrDefault("startTime", null),
                (LocalTime) dtParams.getOrDefault("endTime", null));
        return MealsUtil.getTos(
                super.getAll(authUserId(),
                    (LocalDate) dtParams.getOrDefault("startDate", null),
                    (LocalDate) dtParams.getOrDefault("endDate", null)
                ),
                authUserCaloriesPerDay())
                .stream().filter(filter).collect(Collectors.toList());
    }

    public Meal get(int id) {
        return super.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        return super.create(meal, authUserId());
    }

    public void delete(int id) {
        super.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        super.update(meal, id, authUserId());
    }
}