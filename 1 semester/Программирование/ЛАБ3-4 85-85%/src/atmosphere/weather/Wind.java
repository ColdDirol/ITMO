package atmosphere.weather;

import Exceptions.IllegalNameException;

import static atmosphere.weather.Direction.NORTH;

public class Wind extends Weather{
    public Wind(String name) throws IllegalNameException {
        super(name);
        this.setDirection(NORTH);
    }

    @Override
    public void wind() {
        System.out.println("Ветер дует на " + getDirection());
    }

    @Override
    public String toString() {
        return "Ветер - движение воздуха.";
    }
}
