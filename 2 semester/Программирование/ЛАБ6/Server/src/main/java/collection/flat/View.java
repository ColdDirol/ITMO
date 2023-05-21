package collection.flat;

/**
 * Class View is the helper ENUM class for storing its objects in a Flat object
 */
public enum View {
    YARD("YARD", 1),
    PARK("PARK", 2),
    BAD("BAD", 3),
    GOOD("GOOD", 4);

    private String name;
    private Integer id;

    View(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getTitle() {
        return this.name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer number) {
        this.id = number;
    }

    @Override
    public String toString() {
        return name;
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
