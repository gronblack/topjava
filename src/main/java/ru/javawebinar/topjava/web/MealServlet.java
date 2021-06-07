package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.RamMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2000; // temporary
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Dao<Meal> dao;
    private Logger log;

    @Override
    public void init() {
        dao = new RamMealDao();
        log = getLogger(MealServlet.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // actions
        String action = String.valueOf(request.getParameter("action"));
        switch (action) {
            case "delete":
                String id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    dao.delete(Integer.parseInt(id));
                    log.debug("delete meal:{}", id);
                    response.sendRedirect("meals");
                    log.debug("action Delete: redirect to /meals");
                    return;
                }
                break;
            case "add":
                request.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0));
                log.debug("action Add: forward to /mealEdit.jsp");
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                return;
            case "update":
                id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    request.setAttribute("meal", dao.get(Integer.parseInt(id)));
                    log.debug("action Update: forward to /mealEdit.jsp");
                    request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                }
                return;
        }

        // show all
        List<MealTo> list = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("list", list);
        request.setAttribute("formatter", FORMATTER);
        log.debug("show all, forward to /meals.jsp");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
            log.debug("add meal:{}", meal.getId());
        } else {
            meal.setId(Integer.parseInt(id));
            dao.update(meal);
            log.debug("update meal:{}", meal.getId());
        }
        log.debug("redirect to /meals");
        response.sendRedirect("meals");
    }
}
