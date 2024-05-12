package db.entities;

import beans.Point;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "point_model")
public class PointsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "point_model_id_seq", allocationSize = 1)
    private Long id;
    //private String sessionId;
    private Double x;
    private Double y;
    private Double r;
    private boolean result;

    public PointsEntity(String sessionId, Double x, Double y, Double r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public PointsEntity(Point point) {
        this.x = point.getX();
        this.y = point.getY();
        this.r = point.getR();
        this.result = point.isResult();
    }

    public PointsEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //public String getSessionId() {
    //    return sessionId;
    //}

    //public void setSessionId(String sessionId) {
    //    this.sessionId = sessionId;
    //}

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
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
