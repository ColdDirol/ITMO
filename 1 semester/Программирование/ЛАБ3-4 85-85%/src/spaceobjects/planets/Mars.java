package spaceobjects.planets;

import Exceptions.IllegalNameException;

public class Mars extends Planet {
    public Mars(String name) throws IllegalNameException {
        super(name);
        this.setRadius(3390);
        this.setAttractivePower(3.721);
        this.setMainCity("Марсианский город");
        this.setDayLength(25);
        this.setHaveOxygene(true);
    }
    public void mars(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
    }

    @Override
    public String toString() {
        return "Класс Mars содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и dayLength";
    }
}
