package com.barreirasapp.utils.Route;

import com.barreirasapp.annotation.HttpMethod;

import java.lang.reflect.Method;

public record RouteInfo(
        String path,
        HttpMethod method,
        Method handlerMethod,
        Class<?> controllerClass
) {
}
