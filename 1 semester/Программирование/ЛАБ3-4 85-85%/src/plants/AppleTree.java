package plants;

import Exceptions.IllegalNameException;

import static plants.PlantType.TREE;
import static plants.FruitType.GREEN_APPLE;
import static plants.FruitFlavorType.SOUR;
import static plants.LeavesTypes.GREEN_LEAVES;

public class AppleTree extends Plant {

    public AppleTree(String name) throws IllegalNameException {
        super(name);
        this.setHeight(140);
        this.setFruitSize(7);
        this.setPlantType(TREE);
        this.setFruitType(GREEN_APPLE);
        this.setFruitFlavorType(SOUR);
        this.setLeavesType(GREEN_LEAVES);
    }

    @Override
    public void fruitsCheck() {
        System.out.println("На " + this.getName() + " растут " + this.getFruitType().toString() + ", видно же!");
    }

    @Override
    public void fruitSize(){
        System.out.println("Диаметр " + this.getFruitType().toString() + " примерно: " + this.getFruitSize() + " мм.");
    }

    @Override
    public void fruitFlavor(){
        System.out.println("Вкус " + this.getFruitType() + " " + this.getFruitFlavorType().toString() + ".");
    }

    public void appleTree(){
        System.out.println("Цветущая яблоня символизирует вечную юность, а в Китае — мир и красоту.");
    }
    @Override
    public void height(){
        System.out.println("Высота " + this.getName() + " примерно: " + this.getHeight() + " мм.");
    }

    @Override
    public String toString() {
        return "Класс AppleTree содержит поля: name, height, plantType, fruitType, fruitSize, fruitFlavorType";
    }
}
