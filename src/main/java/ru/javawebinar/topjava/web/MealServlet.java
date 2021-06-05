package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoRam;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000; // temporary

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> list = MealsUtil.filteredByStreams(
                MealDaoRam.getInstance().getAll(),
                LocalTime.MIN,
                LocalTime.MAX,
                CALORIES_PER_DAY
        );

        log.debug("forward to meals");
        request.setAttribute("list", list);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
