package com.barreirasapp.utils.Route;

import java.util.Map;

public final class RouteMatchResult {
    private final boolean matches;
    private final Map<String, String> params;

    public RouteMatchResult(boolean matches, Map<String, String> params) {
        this.matches = matches;
        this.params = params;
    }

    public boolean isMatch() { return matches; }
    public Map<String, String> getParams() { return params; }
}