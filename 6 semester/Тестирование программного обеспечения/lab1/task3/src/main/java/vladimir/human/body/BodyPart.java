package vladimir.human.body;

import lombok.Data;

@Data
public class BodyPart {

    private String name;

    public BodyPart(String name) {
        this.name = name;
    }
}
