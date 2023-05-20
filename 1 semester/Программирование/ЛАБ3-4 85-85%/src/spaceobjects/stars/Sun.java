package spaceobjects.stars;

import Exceptions.IllegalNameException;

public class Sun extends Star{
    public Sun(String name) throws IllegalNameException {
        super(name);
        this.setRadius(696340);
        this.setAttractivePower(274);
        this.setMainCity("Жаркий сити");
        this.setHaveOxygene(true);
    }

    public void sun(){
        System.out.println("На " + this.getName() + " находится " + this.getMainCity());
        System.out.println("В котором живут очень горячие " + this.getName() + " люди.");
    }

    @Override
    public String toString() {
        return "Класс Sun содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity";
    }
}
