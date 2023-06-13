package collection.flat;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class Flat is the main class for storing its objects in a HashMap collection
 */
public class Flat extends CollectionAbstractClass implements CollectionInterface, Comparable<Flat> {
    /**
     * @param id
     * The field can't be null, must be greater than zero and automatically generated
     */
    private Integer id;
    /**
     * @param name
     * The field can't be null
     */
    private String name;
    /**
     * @param coordinates
     * The field can't be null
     */
    private Coordinates coordinates;
    /**
     * @param creationDate
     * The field can't be null, must be automatically generated
     */
    private LocalDateTime creationDate;
    /**
     * @param area
     * The field must be greater than zero
     */
    private float area;
    /**
     * @param numberOfRooms
     * The field must be greater or equals than 0 and lower or equals than 7
     */
    private long numberOfRooms;
    /**
     * @param furnish
     * The field can't be null
     */
    private Furnish furnish;
    /**
     * @param view
     * The field can't be null
     */
    private View view;
    /**
     * @param transport
     * The field can't be null
     */
    private Transport transport;
    /**
     * @param house
     * The field can't be null
     */
    private House house;

    {
        coordinates = new Coordinates();
        house = new House();
    }

    /**
     * Method getId() for get Integer id value
     * @return Integer number
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method setId() for set Integer id value
     * @param id
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Method idGenerator() for automatic id generation
     */
    public void idGenerator(){ //
        int id = Objects.hash(name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);
        if(id < 0){
            id *= (-1);
        }
        this.id = id;
    }

    /**
     * Method getName() for get String name value
     * @return String text
     */
    public String getName() {
        return name;
    }
    /**
     * Method setName() for set String name value
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method getCoordinates() for get Coordinates object from the Float object
     * @return Coordinates object
     */
    public Coordinates getCoordinates(){
        return coordinates;
    }
    /**
     * Method getCoordinatesX() for get Float X coordinate
     * @return Float number
     */
    public Float getCoordinatesX() {
        return coordinates.getX();
    }
    /**
     * Method getCoordinatesY() for get Float Y coordinate
     * @return Float number
     */
    public Float getCoordinatesY(){
        return coordinates.getY();
    }
    /**
     * Method setCoordinates() for set Coordinates object to the Float object
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    /**
     * Method setCoordinatesX() for set Float X coordinate
     * @param x
     */
    public void setCoordinatesX(Float x){
        coordinates.setX(x);
    }
    /**
     * Method setCoordinatesY() for set Float Y coordinate
     * @param y
     */
    public void setCoordinatesY(Float y) {
        coordinates.setY(y);
    }

    /**
     * Method getCreationDate() for get LocalDateTime object from the Float object
     * @return LocalDateTime object
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    /**
     * Method setCreationDate() for set LocalDateTime object to the Float object
     * @param creationDate
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * Method creationDateGenerator() for automatic creationDate generation
     */
    public void creationDateGenerator(){ //  <-- Автоматическое генерирование creationDate
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Method getArea() for get Float number
     * @return Float number
     */
    public float getArea() {
        return area;
    }
    /**
     * Method setArea() for set Float number
     * @param area
     */
    public void setArea(float area) {
        this.area = area;
    }

    /**
     * Method getNumberOfRooms() for get long number
     * @return Long number
     */
    public long getNumberOfRooms() {
        return numberOfRooms;
    }
    /**
     * Method setArea() for set long number
     * @param numberOfRooms
     */
    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    /**
     * Method getFurnish() for get Furnish object from the Float object
     * @return Furnish object
     */
    public Furnish getFurnish() {
        return furnish;
    }
    /**
     * Method setFurnish() for set Furnish object to the Float object
     * @param furnish
     */
    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }
    public Integer getFurnishId() {
        return furnish.getId();
    }

    /**
     * Method getView() for get View object from the Float object
     * @return View object
     */
    public View getView() {
        return view;
    }
    /**
     * Method setView() for set View object to the Float object
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }
    public Integer getViewId() {
        return view.getId();
    }

    /**
     * Method getTransport() for get Transport object from the Float object
     * @return Transport object
     */
    public Transport getTransport() {
        return transport;
    }
    /**
     * Method setTransport() for set Transport object to the Float object
     * @param transport
     */
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
    public Integer getTransportId() {
        return transport.getId();
    }

    /**
     * Method getHouse() for get House object from the Float object
     * @return House object
     */
    public House getHouse() {
        return house;
    }
    /**
     * Method getHouseName() for get String name of House
     * @return String name
     */
    public String getHouseName() {
        return house.getName();
    }
    /**
     * Method getHouseYear() for get Integer year of House
     * @return Integer name
     */
    public Integer getHouseYear() {
        return house.getYear();
    }
    /**
     * Method getHouseYear() for get Integer number of lifts of House
     * @return Integer name
     */
    public Integer getHouseNumberOfLifts() {
        return house.getNumberOfLifts();
    }
    /**
     * Method setHouse() for set House object to the Float object
     */
    public void setHouse(House house) {
        this.house = house;
    }
    /**
     * Method setHouseName() for set String name to the House object
     */
    public void setHouseName(String name) {
        house.setName(name);
    }
    /**
     * Method setHouseYear() for set Integer number of house year to the House object
     */
    public void setHouseYear(int year) {
        house.setYear(year);
    }
    /**
     * Method setHouseNumberOfLifts() for set Integer number of lifts to the House object
     */
    public void setHouseNumberOfLifts(Integer numberOfLifts){
        house.setNumberOfLifts(numberOfLifts);
    }

    /**
     * Method toString() returns information about the fields of the Flat object
     * @return String of all fields with caption
     */
    @Override
    public String print() {
        return "id = " + this.id + "\n" +
                "name = " + this.name + "\n" +
                coordinates.print() +
                "creationDate = " + this.creationDate.toString() + "\n" +
                "area = " + this.area + "\n" +
                "numberOfRooms = " + this.numberOfRooms + "\n" +
                "furnish = " + this.furnish.toString() + "\n" +
                "view = " + this.view.toString() + "\n" +
                "transport = " + this.transport.toString() + "\n" +
                house.print();
    }

    /**
     * The method equals(Object o) is used to compare
     * @param o is the object with wich we will compare the bject that called this method.
     * @return -1, 0 or 1
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat flat)) return false;
        return Float.compare(flat.area, area) == 0 && numberOfRooms == flat.numberOfRooms && Objects.equals(id, flat.id) && Objects.equals(name, flat.name) && Objects.equals(coordinates, flat.coordinates) && Objects.equals(creationDate, flat.creationDate) && furnish == flat.furnish && view == flat.view && transport == flat.transport && Objects.equals(house, flat.house);
    }

    /**
     * The method hashCode() is method for converting Flat object to the hashcode value
     * @return hashcode value of Flat object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, area, numberOfRooms, furnish, view, transport, house);
    }

    /**
     * The method objectSize() is method to get sum of lengths of each field in the Flat class
     * @return int value of every field length from Flat object
     */
    @Override
    public Integer objectSize() {
        return this.<String>size(name)
                + this.coordinates.objectSize()
                + this.<LocalDateTime>size(creationDate)
                + this.<Float>size(area)
                + this.<Long>size(numberOfRooms)
                + this.<Furnish>size(furnish)
                + this.<View>size(view)
                + this.<Transport>size(transport)
                + this.house.objectSize();
    }

    /**
     * Method compareTo() is currently not used
     * @param flat is the object to be compared.
     * @return zero, because currently not used
     */
    @Override
    public int compareTo(Flat flat) {
        return this.objectSize() - flat.objectSize();
    }
}
