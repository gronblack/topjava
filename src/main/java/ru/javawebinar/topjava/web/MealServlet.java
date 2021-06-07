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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_PER_DAY = 2000; // temporary
    private DateTimeFormatter formatter;
    private Dao<Meal, Integer> dao;
    private Logger log;

    @Override
    public void init() throws ServletException {
        super.init();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dao = new MealDaoRam();
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
        request.setAttribute("formatter", formatter);

        // actions
        String action = String.valueOf(request.getParameter("action"));
        String id;
        switch (action) {
            case "delete":
                id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    dao.delete(Integer.valueOf(id));
                    response.sendRedirect("meals");
                    return;
                }
                break;
            case "add":
                request.setAttribute("meal", new Meal(LocalDateTime.now().withSecond(0).withNano(0), "", 0));
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case "update":
                id = request.getParameter("id");
                if (id != null && !id.isEmpty()) {
                    request.setAttribute("meal", dao.get(Integer.valueOf(id)));
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
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Integer.valueOf(id));
            dao.update(meal);
        }
        response.sendRedirect("meals");
    }
}
