package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class ScaryFace extends StatusMove {
    public ScaryFace() {super(Type.NORMAL, 0, 1);}

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPEED, (int)p.getStat(Stat.SPEED) - 2);
    }

    protected String describe() {return "понижает скорость противника на две ступени";}
}

