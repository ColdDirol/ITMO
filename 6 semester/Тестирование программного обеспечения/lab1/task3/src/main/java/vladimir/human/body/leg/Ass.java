package vladimir.human.body.leg;

import vladimir.human.body.OrganAbstract;

public class Ass extends OrganAbstract {

    public Ass(String name, String bodyPartName, String owner) {
        super(name, bodyPartName, owner);
    }

    public void sitTo(String where) {
        System.out.println(getName() + " " + getOwner() + " сидел на " + where);
    }
}
