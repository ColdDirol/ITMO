package beans;

import org.json.simple.JSONObject;

public class TokenBean {
    String token;

    public TokenBean(String token) {
        this.token = token;
    }

    public JSONObject toJson() {
        JSONObject tokenJson = new JSONObject();

        tokenJson.put("token", token);

        return tokenJson;
    }
}