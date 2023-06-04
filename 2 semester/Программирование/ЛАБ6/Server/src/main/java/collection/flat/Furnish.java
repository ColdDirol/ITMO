package collection.flat;

/**
 * Class Furnish is the helper ENUM class for storing its objects in a Flat object
 */
public enum Furnish { // обстановка
    DESIGNER("DESIGNER", 1),
    NONE("NONE", 2),
    FINE("FINE", 3),
    BAD("BAD", 4),
    LITTLE("LITTLE", 5);

    private String name;
    private Integer id;

    Furnish(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public static String getNameById(Integer id) {
        if(id == 1) return DESIGNER.name;
        if(id == 2) return NONE.name;
        if(id == 3) return FINE.name;
        if(id == 4) return BAD.name;
        if(id == 5) return LITTLE.name;
        return null;
    }

    public Integer getId() {
        return this.id;
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
     * @return int value of length of one any type field
     * This method help to the objectSize() method
     */
    private<T> int size(T item) {
        return item.toString().length();
    }
}
