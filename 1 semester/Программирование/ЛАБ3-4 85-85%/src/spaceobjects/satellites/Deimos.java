package spaceobjects.satellites;

import Exceptions.IllegalNameException;

public class Deimos extends Satellite{
    public Deimos(String name) throws IllegalNameException {
        super(name);
        this.setRadius(6);
        this.setAttractivePower(0.003);
        this.setMainCity("Агли сити");
        this.setMainPlanet("Марс");
        this.setDistance(23400);
        this.setHaveOxygene(false);
    }

    public void deimos(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
        System.out.println("В котором живут " + this.getName() + " люди.");
    }

    @Override
    public String toString() {
        return "Класс Deimos содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и mainPlanet, distance";
    }
}
