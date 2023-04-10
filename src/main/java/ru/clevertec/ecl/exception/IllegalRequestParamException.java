package ru.clevertec.ecl.exception;

public class IllegalRequestParamException extends RuntimeException {

    private final int errorCode;

    public IllegalRequestParamException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return "he page must not be negative and the size must be one or higher";
    }
}
