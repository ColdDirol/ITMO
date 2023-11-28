import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class PointsService implements Serializable {
    private static final List<Point> pointList = new ArrayList<>();
    private Point newPoint = new Point();
    private String returnMessage = null;

    public void addPoint() {
        newPoint.setResult(AreaCheckService.check(newPoint.getX(), newPoint.getY(), newPoint.getR()));
        pointList.add(newPoint);
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

            // Преобразование x, y и r при необходимости
            Double parsedX = formatDouble(Double.parseDouble(x));
            Double parsedY = formatDouble(Double.parseDouble(y));
            Double parsedR = formatDouble(Double.parseDouble(r));

            // Установка значений в управляемом бине
            newPoint.setX(parsedX);
            newPoint.setY(parsedY);
            newPoint.setR(parsedR);

            System.out.println(newPoint.getX() + " " + newPoint.getY() + " " + newPoint.getR());
            System.out.println(AreaCheckService.check(newPoint.getX(), newPoint.getY(), newPoint.getR()));

            // Добавление точки с использованием существующего метода addPoint
            addPoint();
        } catch (NumberFormatException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public List<Point> getPointList() {
        return pointList;
    }

    public void clearPointList() {
        pointList.clear();

        // Вызывайте PrimeFaces.update() для обновления компонентов на стороне клиента
        PrimeFaces.current().ajax().update("formId:results-table", "formId:coordinates-canvas");
    }

    public Point getNewPoint() {
        return newPoint;
    }

    public void setNewPoint(Point newPoint) {
        this.newPoint = newPoint;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
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
}
