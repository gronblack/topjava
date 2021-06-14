package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String params = request.getReader().lines().collect(Collectors.joining());
        if (!params.isEmpty()) {
            if ("GetActualUser".equals(params)) {
                log.info("Users: get auth user");
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.append(String.valueOf(SecurityUtil.authUserId()));
                out.close();
                return;
            }
            String[] paramsArray = params.split("=");
            if ("userId".equals(paramsArray[0])) {
                log.info("Users: set auth user");
                SecurityUtil.setAuthUserId(Integer.parseInt(paramsArray[1]));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
