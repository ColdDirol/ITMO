package vladimir.human.body.leg;

import lombok.Getter;
import vladimir.human.body.BodyPartAbstract;

@Getter
public class Legs extends BodyPartAbstract {

    private Ass ass;

    public Legs(String name, String owner) {
        super(name, owner);

        this.ass = new Ass("Жопа", getName(), getOwner());
    }

}
