package vladimir.place;

import lombok.Data;
import vladimir.utils.Validator;

@Data
public abstract class Place {
    private String name;

    public Place(String name) {
        Validator.checkStrings(name, "name");
        this.name = name;

        System.out.println("Существовало место - " + this.name);
    }
}
