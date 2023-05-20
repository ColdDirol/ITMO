package atmosphere.weather;

import Exceptions.IllegalNameException;
import actions.PhysicalObject;
public abstract class Weather extends PhysicalObject {
    private Direction direction = Direction.NORTH;

    public Weather(String name) throws IllegalNameException {
        super(name);
    }

    public abstract void wind();

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Класс Weather содержит поля: name, direction";
    }
}
