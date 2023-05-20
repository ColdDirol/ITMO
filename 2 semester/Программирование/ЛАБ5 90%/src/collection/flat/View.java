package collection.flat;

/**
 * Class View is the helper ENUM class for storing its objects in a Flat object
 */
public enum View {
    YARD("YARD"),
    PARK("PARK"),
    BAD("BAD"),
    GOOD("GOOD");

    private String title;

    View(String title) {
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
     * @return int value of length of one any type field using method toString()
     * This method help to the objectSize() method
     */
    private<T> int size(T item) {
        return item.toString().length();
    }
}
