package collection.flat;

public abstract class CollectionAbstractClass implements CollectionInterface {
    /**
     * The method size() is method to get sum of length of one any type field
     * @return int value of length of one any type field
     * This method help to the objectSize() method
     */
    @Override
    public<T> Integer size(T item) {
        return item.toString().length();
    }
}
