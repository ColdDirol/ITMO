package exceptions;

import java.io.FileNotFoundException;

/**
 * The FileHasBeenException is exception which can detect deleting of XML file.
 */
public class FileHasBeenDeletedException extends FileNotFoundException {
    public FileHasBeenDeletedException(String message) {
        System.err.println("The file " + message + " was deleted!");
        System.err.println("Quitting the program will result in a total loss of data!");
    }
}
