package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        if (StringUtils.hasLength(request.getParameter("userId"))) {
            String userId = request.getParameter("userIdSelect");
            if (StringUtils.hasLength(userId)) {
                log.info("Users: set auth user");
                SecurityUtil.setAuthUserId(Integer.parseInt(userId));
            }
        } else {
            request.setAttribute("userId", SecurityUtil.authUserId());
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
