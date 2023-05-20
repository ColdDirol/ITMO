package spaceobjects.satellites;

import Exceptions.IllegalNameException;

public class Fobos extends Satellite{
    public Fobos(String name) throws IllegalNameException {
        super(name);
        this.setRadius(11267);
        this.setAttractivePower(0.006);
        this.setMainCity("Фобос сити");
        this.setMainPlanet("Марс");
        this.setDistance(6006);
        this.setHaveOxygene(true);
    }

    public void fobos(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
        System.out.println("В котором живут " + this.getName() + " люди.");
    }

    @Override
    public String toString() {
        return "Класс Fobos содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и mainPlanet, distance";
    }
}
