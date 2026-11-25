package exception;

public class CGMInvalidValueException extends CGMException {
    public CGMInvalidValueException(long value) {
        super("Not in range: " + value);
    }
}
