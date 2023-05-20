package spaceobjects.stars;

import Exceptions.IllegalNameException;
import spaceobjects.IlluminateStatus;
import spaceobjects.SpaceObject;
import spaceobjects.SpaceObjectType;

import java.util.Objects;

import static spaceobjects.IlluminateStatus.EMIT_LIGHT;
import static spaceobjects.SpaceObjectType.STAR;

public abstract class Star extends SpaceObject {
    private SpaceObjectType spaceObjectType = SpaceObjectType.STAR;
    private IlluminateStatus illuminateStatus = IlluminateStatus.EMIT_LIGHT;

    public Star(String name) throws IllegalNameException {
        super(name);
        this.setSpaceObjectType(STAR);
        this.setIlluminateStatus(EMIT_LIGHT);
    }

    public void Star(){
        System.out.println("Звезда - самосветящееся небесное тело, состоящее из газа и плазмы");
        System.out.println("Звезда " + this.illuminateStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Star star)) return false;
        if (!super.equals(o)) return false;
        return spaceObjectType == star.spaceObjectType && illuminateStatus == star.illuminateStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), spaceObjectType, illuminateStatus);
    }

    @Override
    public String toString() {
        return "Класс Star содержит поля: name, illuminateStatus, spaceObjectType, radius, attractivePower, mainCity";
    }
}
