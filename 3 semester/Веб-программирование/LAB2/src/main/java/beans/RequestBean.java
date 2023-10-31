package beans;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import services.JSONService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

/**
 * Request bean.
 * Contains:
 * Double x,
 * Double y,
 * Double R.
 */
public class RequestBean implements Serializable {
    private Double x;
    private Double y;
    private Double R;

    public RequestBean(Double x, Double y, Double R) {
        this.x = x;
        this.y = y;
        this.R = R;
    }

    public RequestBean(HttpServletRequest request) throws IOException, ParseException, NullPointerException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JSONObject json = JSONService.parseToJSONObject(stringBuilder.toString());

        this.x = json.get("x") != null ? Double.parseDouble(json.get("x").toString()) : null;
        this.y = json.get("y") != null ? Double.parseDouble(json.get("y").toString()) : null;
        this.R = json.get("R") != null ? Double.parseDouble(json.get("R").toString()) : null;

        if (x == null || y == null || R == null) {
            throw new NullPointerException("Missing data in JSON.");
        }
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return R;
    }

    public void setR(Double r) {
        R = r;
    }
}
