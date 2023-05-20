package characters.shorties;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.Work;

import static characters.ProfessionType.SCIENTIST;

public class Znayka extends Shorties{
    public Znayka(String name) throws NullException, IllegalNameException {
        super(name);
        this.setSex("Мужчина");
        this.setMass(0.2F);
        this.setProfessionType(SCIENTIST);
        this.setClothes("Классический костюм");
    }

    @Override
    public void work() {
        System.out.println(getName() + " " + this.getProfessionType().toString() + ". Душнит.");
    }

    @Override
    public String toString() {
        return "Класс Znayka содержит поля: name, sex, mass, professionType, clothes";
    }
}
