package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class NightShade extends SpecialMove {
    public NightShade() {super(Type.GHOST, 0, 1);}

    protected void applyOppDamage(Pokemon p) {
        p.setMod(Stat.HP, p.getLevel());
    }

    protected String describe() {return "атакует из последних сил";}
}

