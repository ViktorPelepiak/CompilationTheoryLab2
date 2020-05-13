package part1.exceptions;

public class EndOfLineException extends Exception {
    public EndOfLineException() {
        super();
    }

    EndOfLineException(String message) {
        super(message);
    }
}
