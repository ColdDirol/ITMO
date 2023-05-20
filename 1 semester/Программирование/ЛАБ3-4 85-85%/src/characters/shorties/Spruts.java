package characters.shorties;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.Work;
import characters.ProfessionType;
import spaceobjects.satellites.Moon;

import static characters.ProfessionType.BIG_BOSS;
import static characters.ProfessionType.SLACKER;

public class Spruts extends Shorties{
    public Spruts(String name) throws NullException, IllegalNameException {
        super(name);
        this.setSex("Мужчина");
        this.setMass(0.5F); //0
        this.setProfessionType(BIG_BOSS);
        this.setClothes("Малиновый костюм");
    }

    @Override
    public void work() {
        System.out.println(getName() + " снова ничего не делает, ведь он " + this.getProfessionType().toString() + " и все работают НА НЕГО!");
    }

    @Override
    public String toString() {
        return "Класс Spruts содержит поля: name, sex, mass, professionType, clothes";
    }
}
