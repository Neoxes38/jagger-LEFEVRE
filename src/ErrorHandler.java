package src;

public interface ErrorHandler {
    boolean hasError();
    String getMessage();
    void setError(String s);
} // ErrorHandler
