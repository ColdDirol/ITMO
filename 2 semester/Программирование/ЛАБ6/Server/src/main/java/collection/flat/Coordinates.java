package collection.flat;

/**
 * Class Coordinates is the helper class for storing its objects in a Flat object
 */
public class Coordinates extends CollectionAbstractClass implements CollectionInterface, Comparable<Coordinates>{
    /**
     * @param x can't be null
     */
    private Float x;
    /**
     * @param y can't be null
     */
    private Float y;

    public Float getX() {
        return x;
    }
    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }
    public void setY(Float y) {
        this.y = y;
    }

    /**
     * The method objectSize() is method to get sum of lengths of each field in the Coordinates class
     * @return int value of every field length from Coordinates object
     */
    @Override
    public Integer objectSize() {
        return this.<Float>size(x)
                + this.<Float>size(y);
    }

    /**
     * Method compareTo() is currently not used
     * @param coordinates is the object to be compared.
     * @return zero, because currently not used
     */
    @Override
    public int compareTo(Coordinates coordinates) {
        return this.objectSize() - coordinates.objectSize();
    }

    @Override
    public String print() {
        return "Coordinate X: " + this.x + "\n" +
                "Coordinate Y: " + this.y + "\n";
    }
}