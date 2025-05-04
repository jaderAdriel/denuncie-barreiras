package com.barreirasapp.utils.routes;

import java.util.Map;

public final class RouteMatchResult {
    private final boolean matches;
    private final RouteInfo routeInfo;
    private final Map<String, String> params;

    public RouteMatchResult(boolean matches, RouteInfo routeInfo, Map<String, String> params) {
        this.matches = matches;
        this.params = params;
        this.routeInfo = routeInfo;
    }

    public boolean isMatch() { return matches; }
    public RouteInfo getRouteInfo() { return routeInfo; }
    public Map<String, String> getParams() { return params; }
}