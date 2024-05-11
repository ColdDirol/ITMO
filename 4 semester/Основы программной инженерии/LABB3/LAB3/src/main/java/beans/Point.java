package beans;

import java.io.Serializable;

public class Point implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private boolean result;

    public Double getX() {
        return x;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public void setX(String x) {
        this.x = Double.parseDouble(x);
    }

    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public void setY(String y) {
        this.y = Double.parseDouble(y);
    }


    public Double getR() {
        return r;
    }
    public void setR(Double r) {
        this.r = r;
    }
    public void setR(String r) {
        this.r = Double.parseDouble(r);
    }

    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
}
