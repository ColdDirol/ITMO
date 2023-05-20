package plants;

import Exceptions.IllegalNameException;
import actions.PhysicalObject;

import java.util.Objects;

public abstract class Plant extends PhysicalObject {
    private int height;
    private PlantType plantType;
    private FruitType fruitType;
    private int fruitSize;
    private FruitFlavorType fruitFlavorType;
    private LeavesTypes leavesType;

    public Plant(String name) throws IllegalNameException {
        super(name);
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public PlantType getPlantType() {
        return plantType;
    }
    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public FruitType getFruitType() {
        return fruitType;
    }
    public void setFruitType(FruitType fruitType) {
        this.fruitType = fruitType;
    }

    public int getFruitSize() {
        return fruitSize;
    }
    public void setFruitSize(int fruitSize) {
        this.fruitSize = fruitSize;
    }

    public FruitFlavorType getFruitFlavorType() {
        return fruitFlavorType;
    }
    public void setFruitFlavorType(FruitFlavorType fruitFlavorType) {
        this.fruitFlavorType = fruitFlavorType;
    }

    public LeavesTypes getLeavesType() {
        return leavesType;
    }
    public void setLeavesType(LeavesTypes leavesType) {
        this.leavesType = leavesType;
    }

    public void leavesCheck() {
        System.out.println("На " + this.getName() + " растут " + this.getLeavesType().toString());
    }
    public void leavesSwayed() {
        System.out.println(this.getLeavesType().toString() + " качались.");
    }
    public abstract void fruitsCheck();
    public abstract void height();
    public abstract void fruitSize();
    public abstract void fruitFlavor();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant plant)) return false;
        if (!super.equals(o)) return false;
        return height == plant.height && fruitSize == plant.fruitSize && plantType == plant.plantType && fruitType == plant.fruitType && fruitFlavorType == plant.fruitFlavorType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height, plantType, fruitType, fruitSize, fruitFlavorType);
    }

    @Override
    public String toString() {
        return "Класс Plant содержит поля: name, height, plantType, fruitType, fruitSize, fruitFlavorType";
    }
}
