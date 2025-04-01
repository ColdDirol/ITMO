package vladimir.human.body.head;

import vladimir.human.body.OrganAbstract;

public class Teeth extends OrganAbstract {

    public Teeth(String name, String bodyPartName, String owner) {
        super(name, bodyPartName, owner);
    }

    public void touchWithHands() {
        System.out.println(
                getOwner()
                        + " ковырялся в "
                        + getName()
                        + " "
                        + getBodyPartName()
                        + " руками"
        );
    }
}
