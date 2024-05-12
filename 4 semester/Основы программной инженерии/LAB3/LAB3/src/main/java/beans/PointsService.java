package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

import db.entities.PointsEntity;
import db.repositories.PointRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import java.text.DecimalFormat;
import java.util.List;

@Named
@RequestScoped
public class PointsService implements Serializable {
    @Inject
    private SessionBean sessionBean;

    private PointRepository pointRepository = new PointRepository();

    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    private Point newPoint = new Point();

    public void addPoint() {
        newPoint.setResult(AreaCheckService.check(newPoint.getX(), newPoint.getY(), newPoint.getR()));
        sessionBean.addPoint(newPoint);

        //add to db
        pointRepository.addNew(new PointsEntity(newPoint));

        newPoint = new Point();

        PrimeFaces.current().executeScript("drawFuckingPoint()");
        PrimeFaces.current().ajax().update("coordinates-canvas");
    }

    public Double formatDouble(Double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(number);
        return Double.parseDouble(formattedNumber);
    }

    public void receivePointData() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Point point = new Point();

            String x = (String) context.getExternalContext().getRequestParameterMap().get("x");
            String y = (String) context.getExternalContext().getRequestParameterMap().get("y");
            String r = (String) context.getExternalContext().getRequestParameterMap().get("r");

            Double parsedX = formatDouble(Double.parseDouble(x));
            Double parsedY = formatDouble(Double.parseDouble(y));
            Double parsedR = formatDouble(Double.parseDouble(r));

            newPoint.setX(parsedX);
            newPoint.setY(parsedY);
            newPoint.setR(parsedR);

            addPoint();
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Point> getPointList() {
        return sessionBean.getPointList();
    }

    public void clearPointList() {
        sessionBean.clearPointList();
        pointRepository.deleteAll();
        System.out.println("Table has been cleared!");

        PrimeFaces.current().executeScript("PF('resultsTable').clearFilters()");
        PrimeFaces.current().ajax().update("formId:results-table", "formId:coordinates-canvas");
    }


    public Point getNewPoint() {
        return newPoint;
    }

    public void setNewPoint(Point newPoint) {
        this.newPoint = newPoint;
    }

    public void setX(Double x) {
        newPoint.setX(x);
    }

    public void setY(Double y) {
        newPoint.setY(y);
    }

    public void setR(Double r) {
        newPoint.setR(r);
    }

    // Геттер и сеттер для sessionBean

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }
}