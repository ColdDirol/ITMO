package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;
public class FocusBlast extends SpecialMove {
    public FocusBlast() {super(Type.FIGHTING, 120, 0.7);}


    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() <= 0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE, (int) (p.getStat(Stat.SPECIAL_DEFENSE)) - 1);
        }
    }

    protected String describe() {
        return "Снижает защиту цели на одну ступень";
    }
}

