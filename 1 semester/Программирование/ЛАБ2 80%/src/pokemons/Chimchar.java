package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Chimchar extends Pokemon {
    public Chimchar(String name, int level){
        super(name, level);
        setStats(44, 58, 44, 58, 44, 61);
        setType(Type.FIRE);
        setMove(new LowSweep(), new Rest());
    }
}



