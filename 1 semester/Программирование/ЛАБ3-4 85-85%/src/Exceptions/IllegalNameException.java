package Exceptions;

public class IllegalNameException extends RuntimeException {
    public IllegalNameException(String description){
        super(description);
    }
}
