package com.barreirasapp.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorResponse {
    private int httpStatus;
    private String message;
    private String redirectLocation;

    public ErrorResponse(int httpStatus, String message, String redirectLocation) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.redirectLocation = redirectLocation;
    }

    public ErrorResponse withRedirect(String location) {
        this.redirectLocation = location;
        return this;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void send(HttpServletResponse resp) {
        try {
            if (redirectLocation != null) {
                resp.sendRedirect(redirectLocation);
            } else {
                resp.sendError(httpStatus, message);
            }
        } catch (IOException ignored) {
        }
    }
}