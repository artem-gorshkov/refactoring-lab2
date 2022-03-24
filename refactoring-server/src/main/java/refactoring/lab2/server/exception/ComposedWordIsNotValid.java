package refactoring.lab2.server.exception;

public class ComposedWordIsNotValid extends RuntimeException {

    public ComposedWordIsNotValid(String message) {
        super(message);
    }

    public ComposedWordIsNotValid(String message, Throwable reason) {
        super(message, reason);
    }

}
