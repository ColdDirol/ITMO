package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Monferno extends Pokemon {
    public Monferno(String name, int level){
        super(name, level);
        setStats(64, 78, 52, 78, 52, 81);
        setType(Type.FIRE);
        setMove(new LowSweep(), new Rest(), new SlackOff());
    }
}

