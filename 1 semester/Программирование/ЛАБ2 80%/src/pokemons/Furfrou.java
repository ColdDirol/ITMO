package pokemons;
import moves.Facade;
import ru.ifmo.se.pokemon.*;
import moves.*;

public class Furfrou extends Pokemon {
    public Furfrou(String name, int level){
        super(name, level);

        setStats(75, 80, 60, 65, 90, 102); // HP, attack, defence, specAtt, specDef, speed
        setType(Type.NORMAL); // type of pokemon
        setMove(new Tackle(), new ThunderWave(), new Facade(), new DarkPulse()); // add moves
    }
}
