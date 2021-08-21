package ru.javawebinar.topjava.util.exception;

import org.slf4j.Logger;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String typeMessage;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
        typeMessage = type.getMessage();
    }

    public ErrorInfo(CharSequence url, ErrorType type, String detail, String typeMessage) {
        this.url = url.toString();
        this.type = type;
        this.detail = detail;
        this.typeMessage = typeMessage;
    }

    public static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType, String message, Logger log) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.getMessage(), message);
    }

    public static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType, Logger log) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.getMessage());
    }

    public static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, Logger log) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.getMessage());
    }
}