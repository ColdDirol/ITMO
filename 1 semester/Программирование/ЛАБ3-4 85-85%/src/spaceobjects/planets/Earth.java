package spaceobjects.planets;

import Exceptions.IllegalNameException;

public class Earth extends Planet {
    public Earth(String name) throws IllegalNameException {
        super(name);
        this.setRadius(6371);
        this.setAttractivePower(9.806);
        this.setMainCity("Цветочный город");
        this.setDayLength(24);
        this.setHaveOxygene(true);
    }
    public void earth(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
    }

    @Override
    public String toString() {
        return "Класс Earth содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и dayLength";
    }
}
