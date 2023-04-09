package ru.clevertec.ecl.exception;

public class EssenceNotFoundException extends RuntimeException {

    private final int errorCode;

    public EssenceNotFoundException(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return "Requested resource was not found";
    }

    public int getErrorCode() {
        return errorCode;
    }
}
