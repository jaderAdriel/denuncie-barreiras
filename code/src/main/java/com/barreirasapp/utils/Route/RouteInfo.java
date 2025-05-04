package com.barreirasapp.utils.Route;

import com.barreirasapp.annotation.HttpMethod;

import java.lang.reflect.Method;
import java.util.List;

public record RouteInfo(
        String path,
        List<HttpMethod> methods,
        Method handlerMethod,
        Class<?> controllerClass
) {
    public boolean supportsMethod(HttpMethod method) {
        return methods.contains(method);
    }
}
