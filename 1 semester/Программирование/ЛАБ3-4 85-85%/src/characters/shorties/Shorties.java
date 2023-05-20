package characters.shorties;

import Exceptions.IllegalNameException;
import Exceptions.NullException;
import actions.Work;
import characters.Creature;
import characters.ProfessionType;

import java.util.Objects;

public abstract class Shorties extends Creature implements Work {
    private ProfessionType professionType;

    Shorties(String name) throws IllegalNameException {
        super(name);
    }

    public ProfessionType getProfessionType() {
        return professionType;
    }
    public void setProfessionType(ProfessionType professionType) {
        this.professionType = professionType;
    }

    @Override
    public void weight() { //Вес (P = m * g)
        System.out.println("Вес " + this.getName() + " равен " + (float)(this.getMass() * 9.8));
    }

    @Override
    public void breathe() { //Вздох
        System.out.println(this.getName() + " вздохнул полной груью.");
    }

    @Override
    public void relax() { //Успокоиться
        System.out.println(this.getName() + " успокоился.");
    }

    @Override
    public void fun() { //Повеселеть
        System.out.println(this.getName() + " повеселел.");
    }

    @Override
    public void easy() { //Стало легче
        System.out.println(this.getName() + " стало легче.");
    }

    public void sour() { //Стало легче
        System.out.println(this.getName() + " стало кисло.");
    }

    public void clothes(){
        System.out.println(this.getName() + " сейчас в " + this.getClothes() + ".");
    }


    public void hideItem(String item) {
        System.out.println(this.getName() + " спрятал " + item);
    }
    public void tasteFruit(String fruit){
        System.out.println(this.getName() + " попробовал " + fruit + ".");
    }
    public void lookAround(){
        System.out.println(this.getName() + " начал осматриваться.");
    }

    public void fear(){
        System.out.println(this.getName() + " боится.");
    }
    public void postFlyingEffect(){
        System.out.println("Ноги " + this.getName() + " подкашивались.");
    }
    public void putOffClothes(String clothes){
        System.out.println(this.getName() + " снял с себя" + clothes);
    }
    public void canBreathe(){
        System.out.println(this.getName() + " может дышать.");
    }
    public void landing(){
        System.out.println(this.getName() + " приземлился.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shorties shorties)) return false;
        if (!super.equals(o)) return false;
        return professionType == shorties.professionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), professionType);
    }

    @Override
    public String toString() {
        return "Класс Shorties содержит поля: name, sex, mass, professionType, clothes";
    }
}
