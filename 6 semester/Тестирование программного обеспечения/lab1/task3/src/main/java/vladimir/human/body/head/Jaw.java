package vladimir.human.body.head;

import lombok.Getter;
import vladimir.human.body.OrganAbstract;

@Getter
public class Jaw extends OrganAbstract {

    private Teeth teeth;

    public Jaw(String name, String bodyPartName, String owner) {
        super(name, bodyPartName, owner);

        this.teeth = new Teeth("Зубы", getName() + " " + getBodyPartName(), getOwner());
    }

    public void dropping() {
        System.out.println(getName() + " отвисла");
    }
}
