package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Cofagrigus extends Pokemon {
    public Cofagrigus(String name, int level){
        super(name, level);
        setStats(58, 50, 145, 95, 105, 30);
        setType(Type.GHOST);
        setMove(new Rest(), new NightShade(), new DoubleTeam(), new ScaryFace());
    }
}


