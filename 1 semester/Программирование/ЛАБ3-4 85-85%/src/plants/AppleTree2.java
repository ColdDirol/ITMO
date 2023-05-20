package plants;

import Exceptions.IllegalNameException;

import static plants.FruitFlavorType.SOUR;
import static plants.FruitType.GREEN_APPLE;
import static plants.PlantType.TREE;
import static plants.LeavesTypes.GREEN_LEAVES;

public class AppleTree2 extends Plant {
    public AppleTree2(String name) throws IllegalNameException {
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

    }

    @Override
    public void height() {

    }

    @Override
    public void fruitSize() {

    }

    @Override
    public void fruitFlavor() {

    }
}
