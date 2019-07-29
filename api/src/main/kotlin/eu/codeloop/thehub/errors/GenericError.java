package eu.codeloop.thehub.errors;

public class GenericError extends Throwable implements Error {
    public static final String GENERIC_ERROR_CODE = "GE";

    private int status;
    private String code = GENERIC_ERROR_CODE;
    private String message;

    public GenericError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
