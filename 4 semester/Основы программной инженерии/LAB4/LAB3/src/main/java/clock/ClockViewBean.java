package clock;

import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.time.LocalDateTime;

@ManagedBean
@ViewScoped
public class ClockViewBean implements Serializable {

    private LocalDateTime dateTime;

    @PostConstruct
    public void init() {
        dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}