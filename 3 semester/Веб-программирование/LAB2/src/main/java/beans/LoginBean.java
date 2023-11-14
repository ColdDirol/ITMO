package beans;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import services.JSONService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class LoginBean {
    private String username;
    private String password;

    public LoginBean(HttpServletRequest request) throws IOException, ParseException, NullPointerException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JSONObject json = JSONService.parseToJSONObject(stringBuilder.toString());

        this.username = json.get("username") != null ? json.get("username").toString() : null;
        this.password = json.get("password") != null ? json.get("password").toString() : null;

        if (username == null || password == null) {
            throw new NullPointerException("Missing data in JSON.");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
