package eu.codeloop.thehub.errors;

public interface Error {
    int getStatus();
    String getCode();
    String getMessage();
}
