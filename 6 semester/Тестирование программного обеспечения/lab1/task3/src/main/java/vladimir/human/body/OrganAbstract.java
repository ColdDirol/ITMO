package vladimir.human.body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OrganAbstract {

    private String name;
    private String bodyPartName;
    private String owner;

    public OrganAbstract(String name, String bodyPartName, String owner) {
        this.name = name;
        this.bodyPartName = bodyPartName;
        this.owner = owner;
    }

    public String getOwner() {
        return owner + "а";
    }
}
