package plants;

import Exceptions.IllegalNameException;

import static plants.FruitFlavorType.WITHOUT_FLAVOR;
import static plants.PlantType.TREE;
import static plants.FruitType.LUNAR_PEAR;
import static plants.LeavesTypes.YELLOW_LEAVES;

public class PearTree extends Plant{
    public PearTree(String name) throws IllegalNameException {
        super(name);
        this.setHeight(140);
        this.setFruitSize(10);
        this.setPlantType(TREE);
        this.setFruitType(LUNAR_PEAR);
        this.setFruitFlavorType(WITHOUT_FLAVOR);
        this.setLeavesType(YELLOW_LEAVES);
    }

    @Override
    public void fruitsCheck() {
        System.out.println("На " + this.getName() + " растут " + this.getFruitType().toString() + ", видно же!");
    }
    @Override
    public void fruitSize(){
        System.out.println("Диаметр" + this.getFruitType().toString() + " примерно: " + this.getFruitSize() + " мм.");
    }

    @Override
    public void fruitFlavor(){
        System.out.println("Вкус " + this.getFruitType().toString() + " " + this.getFruitFlavorType().toString() + ".");
    }

    public void pearTree(){
        System.out.println("Груша - листопадное плодовое быстрорастущее дерево с узкой или овальной короной.");
    }

    @Override
    public void height(){
        System.out.println("Высота " + this.getName() + " примерно: " + this.getHeight() + " мм.");
    }

    @Override
    public String toString() {
        return "Класс PearTree содержит поля: name, height, plantType, fruitType, fruitSize, fruitFlavorType";
    }
}
