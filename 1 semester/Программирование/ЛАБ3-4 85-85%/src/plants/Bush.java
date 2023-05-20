package plants;

import Exceptions.IllegalNameException;

import static plants.FruitType.LUNAR_RESPBERRY;
import static plants.PlantType.BUSH;
import static plants.LeavesTypes.GREEN_LEAVES;

public class Bush extends Plant{

    public Bush(String name) throws IllegalNameException {
        super(name);
        this.setHeight(80);
        this.setPlantType(BUSH);
        this.setFruitType(LUNAR_RESPBERRY);
        this.setLeavesType(GREEN_LEAVES);
    }

    @Override
    public void fruitsCheck() {
        System.out.println("Это " + this.getName() + " какие фрукты?!");
    }

    @Override
    public void fruitSize() {
        System.out.println("На " + this.getName() + " растет " + this.getFruitType().toString());
    }

    @Override
    public void fruitFlavor(){
        System.out.println("На " + this.getName() + " растет " + this.getFruitType().toString());
    }

    public void bush(){
        System.out.println("Куст - Растение с древовидными ветвями, начинающимися почти от самой поверхности земли.");
    }

    @Override
    public void height(){
        System.out.println("Высота " + this.getName() + " примерно: " + this.getHeight() + " мм.");
    }

    @Override
    public String toString() {
        return "Класс Bush содержит поля: name, height, plantType, fruitType, fruitSize, fruitFlavorType";
    }
}
