package beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

import db.entities.PointsEntity;
import db.repositories.PointRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import mbeans.HitPercentage;
import mbeans.PointCounter;
import mbeans.PointNotificationListener;
import org.primefaces.PrimeFaces;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.List;

@Named
@RequestScoped
public class PointsService implements Serializable {
    @Inject
    private SessionBean sessionBean;

    private PointRepository pointRepository = new PointRepository();

    private Point newPoint = new Point();

    @Inject
    private PointCounter pointCounter;
    @Inject
    private HitPercentage hitPercentage;

    @PostConstruct
    public void init() {
        registerMBeans();
    }

    private void registerMBeans() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName pointCounterName = new ObjectName("beans:type=PointCounter");
            ObjectName hitPercentageName = new ObjectName("beans:type=HitPercentage");

            if (!mbs.isRegistered(pointCounterName)) {
                mbs.registerMBean(pointCounter, pointCounterName);
            }

            if (!mbs.isRegistered(hitPercentageName)) {
                mbs.registerMBean(hitPercentage, hitPercentageName);
            }

            mbs.addNotificationListener(pointCounterName, new PointNotificationListener(), null, null);
        } catch (InstanceAlreadyExistsException e) {
            System.err.println("MBean уже существует: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPoint() {
        boolean isInArea = AreaCheckService.check(newPoint.getX(), newPoint.getY(), newPoint.getR());
        newPoint.setResult(isInArea);
        sessionBean.addPoint(newPoint);

        pointCounter.addPoint(isInArea);
        hitPercentage.addPoint(isInArea);

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

            System.out.println("X: " + parsedX);
            System.out.println("Y: " + parsedY);
            System.out.println("R: " + parsedR);

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