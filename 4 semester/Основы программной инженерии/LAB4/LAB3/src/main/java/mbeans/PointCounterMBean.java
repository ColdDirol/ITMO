package mbeans;

public interface PointCounterMBean {
    int getTotalPoints();
    int getPointsInArea();
    void addPoint(boolean isInArea);
}
