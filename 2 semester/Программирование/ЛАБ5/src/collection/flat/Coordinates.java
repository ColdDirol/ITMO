package collection.flat;

import java.time.LocalDateTime;

/**
 * Class Coordinates is the helper class for storing its objects in a Flat object
 */
public class Coordinates implements Comparable<Coordinates>{
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
    public int objectSize() {
        return this.<Float>size(x)
                + this.<Float>size(y);
    }

    /**
     * The method size() is method to get sum of length of one any type field
     * @return int value of length of one any type field
     * This method help to the objectSize() method
     */
    private<T> int size(T item) {
        return item.toString().length();
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
}