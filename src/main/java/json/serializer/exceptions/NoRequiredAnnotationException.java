package json.serializer.exceptions;

public class NoRequiredAnnotationException extends Exception {
    private String message;
    public NoRequiredAnnotationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
