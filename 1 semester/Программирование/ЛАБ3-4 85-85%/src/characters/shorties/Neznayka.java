package characters.shorties;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.Work;

import static characters.ProfessionType.SLACKER;

public class Neznayka extends Shorties{
    public Neznayka(String name) throws NullException, IllegalNameException {
        super(name);
        this.setSex("Мужчина");
        this.setMass(0.2F);
        this.setProfessionType(SLACKER);
        this.setClothes("Скафандр");
    }

    @Override
    public void work() {
        System.out.println(this.getName() + " снова ничего не деает, ведь он " + this.getProfessionType().toString() + ".");
    }

    @Override
    public String toString() {
        return "Класс Neznayka содержит поля: name, sex, mass, professionType, clothes";
    }
}
