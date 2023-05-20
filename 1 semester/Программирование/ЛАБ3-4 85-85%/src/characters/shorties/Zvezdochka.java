package characters.shorties;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.Work;

import static characters.ProfessionType.JOURNALIST;
import static characters.ProfessionType.SCIENTIST;

public class Zvezdochka extends Shorties{
    public Zvezdochka(String name) throws NullException, IllegalNameException {
        super(name);
        this.setSex("Женщина");
        this.setMass(0.1F);
        this.setProfessionType(JOURNALIST);
        this.setClothes("Красное платье");
    }

    @Override
    public void work() {
        System.out.println(getName() + " " + this.getProfessionType().toString() + ". Но в данный момент совращает Незнайку.");
    }

    @Override
    public String toString() {
        return "Класс Zvezdochka содержит поля: name, sex, mass, professionType, clothes";
    }
}
