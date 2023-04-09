package ru.clevertec.ecl.exception;

public class EssenceExistException extends RuntimeException {

    private final int errorCode;

    public EssenceExistException(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return "Tag is already existing";
    }

    public int getErrorCode() {
        return errorCode;
    }
}
