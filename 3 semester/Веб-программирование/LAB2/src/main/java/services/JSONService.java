package services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONService {
    public static JSONObject parseToJSONObject(String string) throws ParseException {
        return (JSONObject) new JSONParser().parse(string);
    }

    public static JSONObject parseToResponceJSON(Double x, Double y, Double R, Boolean result, String compiledIn) {
        JSONObject responseJson = new JSONObject();
        responseJson.put("x", x);
        responseJson.put("y", y);
        responseJson.put("R", R);
        responseJson.put("result", result);
        responseJson.put("compiled_in", compiledIn);

        return responseJson;
    }
}
