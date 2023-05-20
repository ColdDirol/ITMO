package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class DarkPulse extends SpecialMove {
    public DarkPulse() {
        super(Type.DARK, 80, 1);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() <= 0.2) Effect.flinch(p);
    }

    protected String describe() {
        return "атакует противника Электроволной";
    }
}

