package catalog.domain.exceptions;

public class NoStacktraceException extends RuntimeException {

    public NoStacktraceException(final String message) {
        this(message, null);
    }

    // add NO stackTrace here in runTimeException
    public NoStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }

}
