package vladimir.furniture;

import lombok.Getter;
import lombok.Setter;
import vladimir.utils.Validator;

@Getter
@Setter
public abstract class FurnitureAbstract {
    private String name;

    public FurnitureAbstract(String name) {
        Validator.checkStrings(name);
        this.name = name;
    }
}
