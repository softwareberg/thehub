package eu.codeloop.thehub.errors;

public class GenericError implements Error {
    public static final String GENERIC_ERROR_CODE = "GE";

    private int status;
    private String code = GENERIC_ERROR_CODE;
    private String message;

    public GenericError(int status, String code, String message) {
        this.status = status;
        this.code = code;
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
