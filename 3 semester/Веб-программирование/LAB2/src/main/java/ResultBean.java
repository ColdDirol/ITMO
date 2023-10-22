import java.time.LocalDateTime;

public class ResultBean {
    private Double x;
    private Double y;
    private Double R;
    private boolean result;
    private LocalDateTime compiledIn;


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

    public LocalDateTime getCompiledIn() {
        return compiledIn;
    }
    public void setCompiledIn(LocalDateTime compiledIn) {
        this.compiledIn = compiledIn;
    }
}
