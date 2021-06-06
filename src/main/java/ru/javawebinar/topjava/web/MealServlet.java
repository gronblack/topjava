package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDaoRam;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private final Dao<Meal, Integer> dao = MealDaoRam.getInstance();
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    private static final int CALORIES_PER_DAY = 2000; // temporary

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("formatter", formatter);

        // actions
        Map<String, Object> params = getParameters(request);
        String action = String.valueOf(params.get("action"));
        switch (action) {
            case "delete":
                if (params.containsKey("id")) {
                    dao.delete((Integer) params.get("id"));
                    response.sendRedirect("meals");
                    return;
                }
                break;
            case "add":
                request.setAttribute("meal", new Meal(LocalDateTime.now(), "", 0));
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case "update":
                if (params.containsKey("id")) {
                    request.setAttribute("meal", dao.get((Integer) params.get("id")));
                    request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                }
                break;
        }

        // show all
        List<MealTo> list = MealsUtil.filteredByStreams(
                dao.getAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                CALORIES_PER_DAY
        );
        log.debug("forward to meals");
        request.setAttribute("list", list);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> params = getParameters(request);
        Meal meal = new Meal((LocalDateTime) params.get("datetime"), (String) params.get("description"), (Integer) params.get("calories"));
        if (params.get("id") == null) {
            dao.add(meal);
        } else {
            dao.update((Integer) params.get("id"), meal);
        }
        response.sendRedirect("meals");
    }

    private static Map<String, Object> getParameters(HttpServletRequest request) {
        try { request.setCharacterEncoding("UTF-8"); } catch (UnsupportedEncodingException ignored) {}

        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String key = param.getKey();
            Object value = null;
            switch (key) {
                case "id":
                case "calories":
                    String paramValue = param.getValue()[0];
                    if (!paramValue.isEmpty())
                        value = Integer.valueOf(paramValue);
                    break;
                case "datetime":
                    value = LocalDateTime.parse(param.getValue()[0], formatter);
                    break;
                default:
                    value = param.getValue()[0];
                    break;
            }
            result.put(key, value);
        }
        return result;
    }
}
