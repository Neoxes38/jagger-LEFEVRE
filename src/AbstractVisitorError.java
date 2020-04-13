package src;

public abstract class AbstractVisitorError implements Visitor, ErrorHandler {
    private boolean hasError;
    private String errorMessage;

    @Override
    public boolean hasError() { return hasError; }
    @Override
    public String getMessage() { return errorMessage; }

    @Override
    public void setError(String s) {
        hasError = true;
        errorMessage = "Error -> " + s;
    }
} // AbstractVisitorError
