package mbeans;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

@Named
@Singleton
public class PointCounter extends NotificationBroadcasterSupport implements PointCounterMBean {
    private int totalPoints = 0;
    private int pointsInArea = 0;

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getPointsInArea() {
        return pointsInArea;
    }

    @Override
    public void addPoint(boolean isInArea) {
        totalPoints++;
        if (isInArea) {
            pointsInArea++;
        }

        if (!isInArea) {
            System.out.println("From PointCounter: " + isInArea);
            sendNotification(new Notification("OutOfBounds", this, 0, "Point is out of bounds"));
        }
    }
}
