package Exceptions;

public class NullException extends RuntimeException{
    //checked
    public NullException(String description) {
        super(description);
    }
}
