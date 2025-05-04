package com.barreirasapp.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class ControllerDispatcher {
    public static void sendErrors(Map<String, String> errors, HttpServletRequest req) {
        for (String errorName : errors.keySet()) {
            System.out.println(errorName + "Error" + " : " + errors.get(errorName));
            req.setAttribute(errorName+"Error", errors.get(errorName));
        }
    }
}
