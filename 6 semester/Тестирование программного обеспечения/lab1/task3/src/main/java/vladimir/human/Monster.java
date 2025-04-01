package vladimir.human;

import lombok.Getter;
import lombok.Setter;
import vladimir.human.body.head.Head;

@Getter
@Setter
public class Monster extends Human {

    private Head secondHead;

    public Monster(String name, Integer age) {
        super(name, age);

        this.secondHead = new Head("Вторая голова", name);
    }
}
