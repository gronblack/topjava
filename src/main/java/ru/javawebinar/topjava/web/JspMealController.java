package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    @PostMapping
    public String post(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            super.update(meal, getId(request));
        } else {
            super.create(meal);
        }
        return "redirect:meals";
    }

    @GetMapping
    public ModelAndView get(HttpServletRequest request) {
        String action = request.getParameter("action");
        return switch (action == null ? "all" : action) {
            case "delete" -> actionDelete(request);
            case "create", "update" -> actionCreateUpdate(request, "create".equals(action));
            case "filter" -> actionFilter(request);
            default -> actionAll();
        };
    }

    private ModelAndView actionAll() {
        return new ModelAndView("meals", "meals", super.getAll());
    }

    private ModelAndView actionFilter(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        return new ModelAndView("meals", "meals", getBetween(startDate, startTime, endDate, endTime));
    }

    private ModelAndView actionCreateUpdate(HttpServletRequest request, boolean create) {
        final Meal meal = create ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                super.get(getId(request));
        return new ModelAndView("mealForm", "meal", meal);
    }

    private ModelAndView actionDelete(HttpServletRequest request) {
        super.delete(getId(request));
        return new ModelAndView("redirect:meals");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
