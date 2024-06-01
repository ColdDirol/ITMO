package mbeans;

public interface HitPercentageMBean {
    double getHitPercentage();
    void addPoint(boolean isInArea);
}
