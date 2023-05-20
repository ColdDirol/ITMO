package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {super(Type.NORMAL, 0, 0);}

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, (int)p.getStat(Stat.EVASION) + 1);
    }
    protected String describe() {return "повышает своё уклонение на одну ступень";}
}


