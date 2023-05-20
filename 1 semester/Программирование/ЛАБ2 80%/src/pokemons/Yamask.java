package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Yamask extends Pokemon {
    public Yamask(String name, int level){
        super(name, level);
        setStats(38, 30, 85, 55, 65, 30);
        setType(Type.GHOST);
        setMove(new Rest(), new NightShade(), new DoubleTeam());
    }
}



