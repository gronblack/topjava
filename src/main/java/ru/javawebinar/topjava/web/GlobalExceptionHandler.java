package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DEFAULT_EXCEPTION_VIEW = "exception";
    private static final Map<String, String> URL_VIEW = Map.of(
            "/profile", "profile"
    );
    private static final Map<String, Map<String, String>> ERROR_MESSAGE = Map.of(
            "users_unique_email_idx", Map.of("emailError", "User with this email already exists"),
            "meals_unique_user_datetime_idx", Map.of("datetimeError", "You already have meal with this date/time"));

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetMav(req, e, HttpStatus.CONFLICT, false);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {
        return logAndGetMav(req, e, HttpStatus.INTERNAL_SERVER_ERROR, true);
    }

    private static ModelAndView logAndGetMav(HttpServletRequest req, Exception e, HttpStatus status, boolean isDefault) {
        String requestUrl = req.getRequestURL().toString();
        log.error("Exception at request " + requestUrl, e);

        Throwable rootCause = ValidationUtil.getRootCause(e);
        ModelAndView mav;
        if (isDefault) {
            mav = new ModelAndView(DEFAULT_EXCEPTION_VIEW,
                    Map.of("exception", rootCause, "message", rootCause.toString(), "status", status));
        } else {
            mav = getMavFromUrl(requestUrl)
                    .addAllObjects(getErrorMessageMap(rootCause.getMessage()));
        }
        mav.setStatus(status);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        UserTo userTo = authorizedUser == null ? new UserTo() : authorizedUser.getUserTo();
        mav.addObject("userTo", restoreParameters(req, userTo));
        return mav;
    }

    private static ModelAndView getMavFromUrl(String requestUrl) {
        String name = URL_VIEW.entrySet().stream()
                .filter(entry -> requestUrl.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findAny().orElse(DEFAULT_EXCEPTION_VIEW);
        return new ModelAndView(name);
    }

    private static Map<String, String> getErrorMessageMap(String message) {
        if (StringUtils.hasLength(message)) {
            String lowerCase = message.toLowerCase();
            return ERROR_MESSAGE.entrySet().stream()
                    .filter(entry -> lowerCase.contains(entry.getKey()))
                    .flatMap(stringMapEntry -> stringMapEntry.getValue().entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return Collections.emptyMap();
    }

    // restore old parameters values
    private static UserTo restoreParameters(HttpServletRequest req, UserTo userTo) {
        userTo.setName(req.getParameter("name"));
        userTo.setEmail(req.getParameter("email"));
        userTo.setPassword(req.getParameter("password"));
        userTo.setCaloriesPerDay(Integer.parseInt(req.getParameter("caloriesPerDay")));
        return userTo;
    }
}
