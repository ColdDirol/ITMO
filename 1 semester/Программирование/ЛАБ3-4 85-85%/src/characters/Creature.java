package characters;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.PhysicalObject;
import spaceobjects.SpaceObject;

import java.util.Objects;

public abstract class Creature extends PhysicalObject {
    private String sex;
    private SpaceObject location;
    private float mass;
    private String clothes;

    public Creature(String name) throws IllegalNameException {
        super(name);
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public SpaceObject getLocation() {
        return location;
    }
    public void setLocation(SpaceObject location) {
        this.location = location;
    }

    public float getMass() {
        return mass;
    }
    public void setMass(float mass) throws NullException {
        if(mass > 0){
            this.mass = mass;
        }
        else {
            throw new NullException("Mass of " + this.getName() + " can't be equals of 0");
        }
    }

    public String getClothes() {
        return clothes;
    }
    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public abstract void weight();
    public abstract void breathe();
    public abstract void relax();
    public abstract void fun();
    public abstract void easy();

    public void hungry(){
        System.out.println(this.getName() + " ГОЛОДНЫЙ!");
    }
    public void findingEat(){
        System.out.println(this.getName() + " ИЩЕТ ЕДУ");
    }
    public void cantGetEnough(){
        System.out.println(this.getName() + " НИКАК НЕ МОЖЕТ НАСЫТИТЬСЯ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creature creature)) return false;
        if (!super.equals(o)) return false;
        return Float.compare(creature.mass, mass) == 0 && Objects.equals(sex, creature.sex) && Objects.equals(location, creature.location) && Objects.equals(clothes, creature.clothes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sex, location, mass, clothes);
    }

    @Override
    public String toString() {
        return "Класс Creature содержит поля: name, sex, mass, professionType, clothes";
    }
}
