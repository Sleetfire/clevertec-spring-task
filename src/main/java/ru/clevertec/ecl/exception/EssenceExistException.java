package ru.clevertec.ecl.exception;

import ru.clevertec.ecl.dto.SingleResponseError;

public class EssenceExistException extends RuntimeException {

    private SingleResponseError error;

    public EssenceExistException(SingleResponseError error) {
        this.error = error;
    }

    public EssenceExistException() {
        super();
    }

    public EssenceExistException(String message) {
        super(message);
    }

    public EssenceExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceExistException(Throwable cause) {
        super(cause);
    }

    protected EssenceExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SingleResponseError getError() {
        return error;
    }
}
