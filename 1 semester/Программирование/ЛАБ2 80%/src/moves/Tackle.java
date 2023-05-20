package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;
public class Tackle extends PhysicalMove {
    public Tackle() {
        super(Type.NORMAL, 50, 1);
    }

    protected String describe() {
        return "толкает противника";
    }
}


