package vladimir.human.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BodyPartAbstract implements IBodyPart {

    private String name;
    private String owner;

    public BodyPartAbstract(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    @Override
    public void move() {
        System.out.println(getOwner() + " шевелит " + getName());
    }

    @Override
    public void putOn(String on) {
        System.out.println(getOwner() + " положил " + getName() + " на " + on );
    }
}
