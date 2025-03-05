package vladimir.furniture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Furniture {
    private String name;

    public Furniture(String name) {
        this.name = name;
    }
}
