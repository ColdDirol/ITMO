package pokemons;

import ru.ifmo.se.pokemon.*;
import moves.*;
public class Infernape extends Pokemon {
    public Infernape(String name, int level){
        super(name, level);
        setStats(76, 104, 71, 104, 71, 108);
        setType(Type.FIRE);
        setMove(new LowSweep(), new Rest(), new SlackOff(), new FocusBlast());
    }
}

