package collection.flat;

/**
 * Class Transport is the helper ENUM class for storing its objects in a Flat object
 */
public enum Transport {
    FEW("FEW", 1),
    NONE("NONE", 2),
    ENOUGH("ENOUGH", 3);

    private String name;
    private Integer id;

    Transport(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public static String getNameById(Integer id) {
        if(id == 1) return FEW.name;
        if(id == 2) return NONE.name;
        if(id == 3) return ENOUGH.name;
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
