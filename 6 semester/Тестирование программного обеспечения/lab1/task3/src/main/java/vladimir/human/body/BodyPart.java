package vladimir.human.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BodyPart {

    private String name;

    public BodyPart(String name) {
        this.name = name;
    }
}
