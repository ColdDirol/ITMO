package vladimir.human;

import lombok.Getter;
import lombok.Setter;
import vladimir.human.body.BodyPart;
import vladimir.human.body.Head;

@Getter
@Setter
public class Moster extends Human {
    private BodyPart secondHead;

    public Moster(String name, Integer age) {
        super(name, age);

        this.secondHead = new Head("Вторая голова");
    }
}
