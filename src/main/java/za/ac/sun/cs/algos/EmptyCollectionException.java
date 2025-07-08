package za.ac.sun.cs.algos;

/**
 * Exception thrown when attempting to access an element from an empty
 * collection.
 */
public class EmptyCollectionException extends RuntimeException {

    /**
     * Constructs an EmptyCollectionException with a default message.
     */
    public EmptyCollectionException() {
        super("The collection is empty.");
    }

    /**
     * Constructs an EmptyCollectionException with a custom message.
     * 
     * @param message The custom error message.
     */
    public EmptyCollectionException(String message) {
        super(message);
    }
}
