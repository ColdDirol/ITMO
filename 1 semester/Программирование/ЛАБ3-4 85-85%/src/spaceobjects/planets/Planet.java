package spaceobjects.planets;

import Exceptions.IllegalNameException;
import spaceobjects.IlluminateStatus;
import spaceobjects.SpaceObject;
import spaceobjects.SpaceObjectType;

import java.util.Objects;

import static spaceobjects.IlluminateStatus.ABSORB_LIGHT;
import static spaceobjects.SpaceObjectType.PLANET;

public abstract class Planet extends SpaceObject {
    private int dayLength;
    public Planet(String name) throws IllegalNameException {
        super(name);
        this.setSpaceObjectType(PLANET);
        this.setIlluminateStatus(ABSORB_LIGHT);
    }

    public int getDayLength() {
        return dayLength;
    }
    public void setDayLength(int dayLength) {
        this.dayLength = dayLength;
    }

    public void planet(){
        System.out.println("Планета - Небесное тело, вращающееся вокруг звезда.");
        System.out.println("Планета " + this.getIlluminateStatus().toString());
    }

    @Override
    public String toString() {
        return "Класс Planet содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity и dayLength";
    }
}
