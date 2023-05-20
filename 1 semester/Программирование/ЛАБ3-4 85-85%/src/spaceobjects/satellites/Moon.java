package spaceobjects.satellites;

import Exceptions.IllegalNameException;

public class Moon extends Satellite{
    public Moon(String name) throws IllegalNameException {
        super(name);
        this.setRadius(1737);
        this.setAttractivePower(1.62);
        this.setMainCity("Лунный город");
        this.setMainPlanet("Земля");
        this.setDistance(25);
        this.setHaveOxygene(true);
    }

    public void moon(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
        System.out.println("В котором живут " + this.getName() + " люди.");
    }

    @Override
    public String toString() {
        return "Класс Moon содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и mainPlanet, distance";
    }
}
