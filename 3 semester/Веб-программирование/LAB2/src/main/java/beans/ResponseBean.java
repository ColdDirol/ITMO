package beans;

import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * Response bean.
 * Contains:
 * Double x,
 * Double y,
 * Double R.
 */
public class ResponseBean implements Serializable {
    private Double x;
    private Double y;
    private Double R;
    private boolean result;
    private String compiledIn;

    public ResponseBean(Double x, Double y, Double R, Boolean result, String compiledIn) {
        this.x = x;
        this.y = y;
        this.R = R;
        this.result = result;
        this.compiledIn = compiledIn;
    }

    public JSONObject toJson() {
        JSONObject responseJson = new JSONObject();
        responseJson.put("x", x);
        responseJson.put("y", y);
        responseJson.put("R", R);
        responseJson.put("result", result);
        responseJson.put("compiled_in", compiledIn);

        return responseJson;
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

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }

    public String getCompiledIn() {
        return compiledIn;
    }
    public void setCompiledIn(String compiledIn) {
        this.compiledIn = compiledIn;
    }
}
