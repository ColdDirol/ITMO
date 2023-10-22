package beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ResultBean implements Serializable {
    private Double x;
    private Double y;
    private Double R;
    private boolean result;
    private String compiledIn;


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
