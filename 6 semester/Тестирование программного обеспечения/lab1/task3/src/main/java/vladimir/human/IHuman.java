package vladimir.human;

import vladimir.furniture.Furniture;
import vladimir.place.Place;

public interface IHuman {

    void sit(Furniture furniture);
    void comeIn(Place destination);
    void beSurprised();
    void beNervous();
    void seeSmb(Human creature);
    void move();
    void notBelieve();
}
