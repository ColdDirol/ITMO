package mbeans;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Named
@Singleton
public class HitPercentage implements HitPercentageMBean {
    private int totalPoints = 0;
    private int pointsInArea = 0;

    @Override
    public double getHitPercentage() {
        if (totalPoints == 0) {
            return 0.0;
        }
        System.out.println("Hit: " + pointsInArea / totalPoints * 100 + "%");
        return (double) pointsInArea / totalPoints * 100;
    }

    @Override
    public void addPoint(boolean isInArea) {
        totalPoints++;
        if (isInArea) {
            pointsInArea++;
        }
        getHitPercentage();
    }
}
