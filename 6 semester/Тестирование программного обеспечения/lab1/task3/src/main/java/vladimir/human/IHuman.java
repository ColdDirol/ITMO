package vladimir.human;

import vladimir.human.body.BodyPartAbstract;
import vladimir.place.Place;

public interface IHuman {

    void comeIn(Place destination);
    void move(BodyPartAbstract organ);
}
