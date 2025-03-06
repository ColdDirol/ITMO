package vladimir.human;

import lombok.Data;
import vladimir.human.body.BodyPart;
import vladimir.furniture.Furniture;
import vladimir.human.body.Head;
import vladimir.place.Place;

import java.util.List;

@Data
public class Human implements IHuman {
    private String name;
    private Integer age;

    private BodyPart head;
    private BodyPart body;
    private BodyPart leftHand;
    private BodyPart rightHand;
    private BodyPart leftLeg;
    private BodyPart rightLeg;

    public Human(String name, Integer age) {
        if (name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (age == null || age < 0) throw new IllegalArgumentException("Age cannot be null");

        this.name = name;
        this.age = age;

        this.head = new Head("Голова");
        this.head = new Head("Тело");
        this.head = new Head("Левая рука");
        this.head = new Head("Правая рука");
        this.head = new Head("Левая нога");
        this.head = new Head("Правая нога");

        System.out.println();
        System.out.println(
                "Жил был " + this.age + " летний человек - " + this.name + "."
        );
        System.out.println();

    }

    @Override
    public void sit(Furniture furniture) {
        System.out.println(
                name + " сидел в " + furniture.getName()
        );
    }

    @Override
    public void comeIn(Place destination) {
        System.out.println(
                name + " зашел в " + destination.getName()
        );
    }

    @Override
    public void beSurprised() {
        System.out.println(
                name + " удивился"
        );
    }

    @Override
    public void beNervous() {
        System.out.println(
                name + " нервничал"
        );
    }

    @Override
    public void seeSmb(Human creature) {
        System.out.println(
                name + " увидел " + creature.getName()
        );
    }

    @Override
    public void move(BodyPart organ) {
        System.out.println(name + " шевелит " + organ.getName());
    }

    @Override
    public void notBelieve() {
        System.out.println(
                name + " не верил"
        );
    }
}
