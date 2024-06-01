package mbeans;

import javax.management.Notification;
import javax.management.NotificationListener;

public class PointNotificationListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("Received notification: " + notification.getMessage());
    }
}