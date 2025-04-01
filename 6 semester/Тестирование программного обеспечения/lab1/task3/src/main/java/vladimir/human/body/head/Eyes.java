package vladimir.human.body.head;

import vladimir.human.body.OrganAbstract;
import vladimir.utils.Validator;

public class Eyes extends OrganAbstract {

    public Eyes(String name, String bodyPartName, String owner) {
        super(name, bodyPartName, owner);
    }

    public void seeSmb(String who) {
        Validator.checkStrings(who);
        System.out.println(getBodyPartName() + " " + getOwner() + " увидел " + who + "а");
    }
}
