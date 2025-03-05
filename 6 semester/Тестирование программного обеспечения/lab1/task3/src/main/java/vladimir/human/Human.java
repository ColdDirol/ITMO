package vladimir.human;

import lombok.Data;
import vladimir.human.body.BodyPart;
import vladimir.furniture.Furniture;
import vladimir.place.Place;

import java.util.List;

@Data
public class Human implements IHuman {
    private String name;
    private Integer age;

    private List<BodyPart> organs;

    public Human(String name, Integer age, BodyPart ...organs) {
        if (name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (age == null || age < 0) throw new IllegalArgumentException("Age cannot be null");

        this.name = name;
        this.age = age;

        this.organs = List.of(
                organs
        );

        System.out.println();
        System.out.println(
                "Жил был " + this.age + " летний человек - " + this.name + "."
        );
        System.out.println(
                "У него были: "
        );
        this.organs.forEach(organ -> System.out.print(organ.getName() + ", "));
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
    public void move() {
        organs.forEach(organ -> {
            System.out.println(name + " шевелит " + organ.getName());
        });
    }

    @Override
    public void notBelieve() {
        System.out.println(
                name + " не верил"
        );
    }
}
