package moves;

import ru.ifmo.se.pokemon.*;

public class SlackOff extends StatusMove {
    public SlackOff() {super(Type.NORMAL, 0, 0);}

    public static float randomInBorders() {
        return (float) (Math.random() * 0.5); //from 0 to 0.5: (0 + random * (0.5-0))
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.HP, (int)(p.getStat(Stat.HP) * randomInBorders()));
    }

    protected String describe() {
        return "увеличивает HP в" +
                (double)((int)(randomInBorders() * 100))/100 +
                "раз";
    }
}
