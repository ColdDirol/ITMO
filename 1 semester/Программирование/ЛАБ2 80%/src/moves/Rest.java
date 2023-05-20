package moves;

import ru.ifmo.se.pokemon.*;
import static ru.ifmo.se.pokemon.Effect.sleep;
public class Rest extends StatusMove {
    public Rest() {super(Type.PSYCHIC, 0, 0);}

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.HP, (int)p.getStat(Stat.HP));
        sleep(p); // сон на два хода
    }

    protected String describe() {return "полностью вылечил себя";}
}

