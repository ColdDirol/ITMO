package spaceobjects.satellites;

import Exceptions.IllegalNameException;
import spaceobjects.IlluminateStatus;
import spaceobjects.SpaceObject;
import spaceobjects.SpaceObjectType;

import java.util.Objects;

import static spaceobjects.IlluminateStatus.REFLECT_LIGHT;
import static spaceobjects.SpaceObjectType.SATALLITE;

public abstract class Satellite extends SpaceObject {
    private String mainPlanet;
    private int distance;
    public Satellite(String name) throws IllegalNameException {
        super(name);
        this.setSpaceObjectType(SATALLITE);
        this.setIlluminateStatus(REFLECT_LIGHT);
    }

    public String getMainPlanet() {
        return mainPlanet;
    }
    public void setMainPlanet(String mainPlanet) {
        this.mainPlanet = mainPlanet;
    }

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void Satellite(){
        System.out.println("Спутник - небесное тело, вращающееся вокруг другого объекта в космическом пространстве");
        System.out.println("Спутник " + this.getIlluminateStatus().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Satellite satellite)) return false;
        if (!super.equals(o)) return false;
        return distance == satellite.distance && Objects.equals(mainPlanet, satellite.mainPlanet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mainPlanet, distance);
    }

    @Override
    public String toString() {
        return "Класс Satellite содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и mainPlanet, distance";
    }
}
