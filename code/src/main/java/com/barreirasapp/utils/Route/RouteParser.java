package com.barreirasapp.utils.Route;

import java.util.HashMap;
import java.util.Map;

public final class RouteParser {

    public static RouteMatchResult matchAndExtract(String route, String requestPath) {
        Map<String, String> params = new HashMap<>();

        String[] routeParts = normalizeAndSplit(route);
        String[] requestPathParts = normalizeAndSplit(requestPath);

        if (routeParts.length != requestPathParts.length) {
            return new RouteMatchResult(false, Map.of());
        }

        for (int i = 0; i < routeParts.length; i++) {
            if (isParameter(routeParts[i])) {
                String parameterName = routeParts[i].substring(1, routeParts[i].length()-1);
                String parameterValue = requestPathParts[i];
                params.put(parameterName, parameterValue);

            } else if (!routeParts[i].equals(requestPathParts[i]) ) {
                return new RouteMatchResult(false, Map.of());
            }
        }

        return new RouteMatchResult(true, params);
    }

    public static String[] normalizeAndSplit (String path) {
        return path.replaceAll("^/+|/+$", "").split("/");
    }

    private static boolean isParameter(String segment) {
        return segment.startsWith("{") && segment.endsWith("}");
    }
}
