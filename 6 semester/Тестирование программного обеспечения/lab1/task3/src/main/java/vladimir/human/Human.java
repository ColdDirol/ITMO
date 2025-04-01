package vladimir.human;

import lombok.Data;
import vladimir.human.body.Body;
import vladimir.human.body.BodyPartAbstract;
import vladimir.human.body.Hands;
import vladimir.human.body.leg.Legs;
import vladimir.human.body.head.Head;
import vladimir.place.Place;
import vladimir.utils.Validator;

@Data
public class Human implements IHuman {
    private String name;
    private Integer age;

    private Head head;
    private BodyPartAbstract body;
    private Hands hands;
    private Legs legs;

    public Human(String name, Integer age) {
        Validator.checkStrings(name);
        Validator.checkPositiveIntegers(age);

        if (name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (age == null || age < 0) throw new IllegalArgumentException("Age cannot be null");

        this.name = name;
        this.age = age;

        this.head = new Head("Голова", name);
        this.body = new Body("Тело", name);
        this.hands = new Hands("Руки", name);
        this.legs = new Legs("Ноги", name);

        System.out.println(
                "Жил был " + this.age + " летний человек - " + this.name + "."
        );
    }

    @Override
    public void comeIn(Place destination) {
        Validator.checkObject(destination);
        System.out.println(
                name + " зашел в " + destination.getName()
        );
    }

    @Override
    public void move(BodyPartAbstract bodyPart) {
        Validator.checkObject(bodyPart);
        System.out.println(name + " шевелит " + bodyPart.getName());
    }

}
