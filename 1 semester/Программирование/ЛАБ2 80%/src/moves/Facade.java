package moves;

import ru.ifmo.se.pokemon.*;
import static ru.ifmo.se.pokemon.Status.*;
public class Facade extends PhysicalMove {
    public Facade() {super(Type.NORMAL, 70, 1);}

    @Override
    protected void applySelfEffects(Pokemon p) {
        Status s = p.getCondition();
        if (s == POISON || s == BURN || s == PARALYZE) {
            p.setMod(Stat.ATTACK, (int) p.getStat(Stat.ATTACK) * 2);
        }
    }
    protected String describe() {return "увеличивает силу вдвое";}
}


