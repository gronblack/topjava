package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.AbstractMealController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    @GetMapping
    public ModelAndView all() {
        return new ModelAndView("meals", "meals", super.getAll());
    }

    @GetMapping("/filter")
    public ModelAndView filter(@RequestParam Map<String, String> params) {
        LocalDate startDate = parseLocalDate(params.get("startDate"));
        LocalDate endDate = parseLocalDate(params.get("endDate"));
        LocalTime startTime = parseLocalTime(params.get("startTime"));
        LocalTime endTime = parseLocalTime(params.get("endTime"));
        return new ModelAndView("meals", "meals", super.getBetween(startDate, startTime, endDate, endTime));
    }

    @GetMapping("/delete")
    public ModelAndView deleteById(@RequestParam int id) {
        super.delete(id);
        return new ModelAndView("redirect:/meals");
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("mealForm")
                .addObject("meal", getNew(null))
                .addObject("create", true);
    }

    @GetMapping("/update")
    public ModelAndView update(@RequestParam int id) {
        return new ModelAndView("mealForm")
                .addObject("meal", super.get(id))
                .addObject("create", false);
    }

    @PostMapping("/create")
    public String create(@RequestParam Map<String, String> params) {
        super.create(getNew(params));
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String update(@RequestParam Map<String, String> params) {
        super.update(getNew(params), Integer.parseInt(params.get("id")));
        return "redirect:/meals";
    }

    private Meal getNew(Map<String, String> params) {
        return params == null ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                new Meal(LocalDateTime.parse(params.get("dateTime")), params.get("description"), Integer.parseInt(params.get("calories")));
    }
}
