package spaceobjects;

import Exceptions.IllegalNameException;
import actions.PhysicalObject;

import java.util.Objects;

public abstract class SpaceObject extends PhysicalObject {
    private IlluminateStatus illuminateStatus;
    private SpaceObjectType spaceObjectType;
    private int radius;
    private double attractivePower;
    private String mainCity;
    private boolean haveOxygene;

    public SpaceObject(String name) throws IllegalNameException {
        super(name);
    }

    public IlluminateStatus getIlluminateStatus() {
        return illuminateStatus;
    }
    public void setIlluminateStatus(IlluminateStatus illuminateStatus) {
        this.illuminateStatus = illuminateStatus;
    }

    public SpaceObjectType getSpaceObjectType() {
        return spaceObjectType;
    }
    public void setSpaceObjectType(SpaceObjectType spaceObjectType) {
        this.spaceObjectType = spaceObjectType;
    }

    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getAttractivePower() {
        return attractivePower;
    }
    public void setAttractivePower(double attractivePower) {
        this.attractivePower = attractivePower;
    }

    public String getMainCity() {
        return mainCity;
    }
    public void setMainCity(String mainCity) {
        this.mainCity = mainCity;
    }

    public boolean isHaveOxygene() {
        return haveOxygene;
    }
    public void setHaveOxygene(boolean haveOxygene) {
        this.haveOxygene = haveOxygene;
    }

    public void checkOxygene(){
        if(this.isHaveOxygene()) System.out.println("На " + this.getName() + " есть кислород.");
        else System.out.println("На " + this.getName() + " кисорода нет.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceObject that)) return false;
        if (!super.equals(o)) return false;
        return radius == that.radius && Double.compare(that.attractivePower, attractivePower) == 0 && illuminateStatus == that.illuminateStatus && spaceObjectType == that.spaceObjectType && Objects.equals(mainCity, that.mainCity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), illuminateStatus, spaceObjectType, radius, attractivePower, mainCity);
    }

    @Override
    public String toString() {
        return "Класс SpaceObject содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity";
    }
}
