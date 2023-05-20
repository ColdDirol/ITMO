package moves;

import ru.ifmo.se.pokemon.*;

public class LowSweep extends PhysicalMove {
    public LowSweep() {super(Type.FIGHTING, 65, 1);}

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPEED, (int)p.getStat(Stat.SPEED) - 1);
    }

    protected String describe() {return "понижает скорость противника на одну ступень";}
}

