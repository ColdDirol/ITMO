package com.backend.backend.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryParamParser {

    public static Map<String, String> parseQueryParams(String queryParam) {
        Map<String, String> result = new HashMap<>();
        if (queryParam != null && !queryParam.isEmpty()) {
            String[] pairs = queryParam.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                if (keyValue.length == 2) {
                    result.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return result;
    }
}
