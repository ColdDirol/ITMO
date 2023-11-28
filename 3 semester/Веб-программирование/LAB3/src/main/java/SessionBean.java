import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {
    private List<Point> pointList = new ArrayList<>();

    public List<Point> getPointList() {
        return pointList;
    }

    public void addPoint(Point point) {
        pointList.add(point);
    }

    public void clearPointList() {
        pointList.clear();
    }
}
