package collection.flat;

/**
 * Class Transport is the helper ENUM class for storing its objects in a Flat object
 */
public enum Transport {
    FEW("FEW"),
    NONE("FEW"),
    ENOUGH("FEW");

    private String title;

    Transport(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    /**
     * The method objectSize() is method to get sum of lengths of each field in the Coordinates class
     * @return int value of every field length from Coordinates object
     */
    public int objectSize() {
        return size(this);
    }

    /**
     * The method size() is method to get sum of length of one any type field
     * @return int value of length of one any type field
     * This method help to the objectSize() method
     */
    private<T> int size(T item) {
        return item.toString().length();
    }
}
