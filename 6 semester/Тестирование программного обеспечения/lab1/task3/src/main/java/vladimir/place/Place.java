package vladimir.place;

import lombok.Data;

@Data
public abstract class Place {
    private String name;

    public Place(String name) {
        this.name = name;

        System.out.println();
        System.out.println("Существовало место - " + this.name);
        System.out.println();
    }
}
