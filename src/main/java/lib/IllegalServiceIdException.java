//Zacharias Thorell

package lib;

public class IllegalServiceIdException extends Exception {
    public IllegalServiceIdException() {
        super("Before using the api the service id needs to be set.");
    }
}
