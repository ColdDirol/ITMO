package collection.flat;

/**
 * Class House is the helper class for storing its objects in a Flat object
 */
public class House extends CollectionAbstractClass implements CollectionInterface, Comparable<House>{
    /**
     * @param name can't be null
     */
    private String name; //Поле может быть null
    /**
     * @param year must be greater or equals than 0 and lower or equals than 874
     */
    private int year; //Максимальное значение поля: 874, Значение поля должно быть больше 0
    /**
     * @param numberOfLifts must be greater than 0
     */
    private Integer numberOfLifts; //Значение поля должно быть больше 0


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }


    public Integer getNumberOfLifts() {
        return this.numberOfLifts;
    }
    public void setNumberOfLifts(Integer numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }

    @Override
    public String print() {
        return "houseName = " + this.name + "\n" +
                "houseYear = " + this.year+ "\n" +
                "houseNumberOfLifts = " + numberOfLifts + "\n";
    }

    /**
     * The method objectSize() is method to get sum of lengths of each field in the House class
     * @return int value of every field length from House object
     */
    @Override
    public Integer objectSize() {
        return this.<String>size(name)
                + this.<Integer>size(year)
                + this.<Integer>size(numberOfLifts);
    }

    /**
     * The method size() is method to get sum of length of one any type field
     * @return int value of length of one any type field
     * This method help to the objectSize() method
     */

    /**
     * Method compareTo() is currently not used
     * @param house is the object to be compared.
     * @return zero, because currently not used
     */
    @Override
    public int compareTo(House house) {
        return this.objectSize() - house.objectSize();
    }
}
