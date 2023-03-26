package ru.clevertec.ecl.exception;

import ru.clevertec.ecl.dto.SingleResponseError;

public class EssenceNotFoundException extends RuntimeException {

    private SingleResponseError error;

    public EssenceNotFoundException(SingleResponseError error) {
        this.error = error;
    }

    public EssenceNotFoundException() {
        super();
    }

    public EssenceNotFoundException(String message) {
        super(message);
    }

    public EssenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssenceNotFoundException(Throwable cause) {
        super(cause);
    }

    public SingleResponseError getError() {
        return this.error;
    }
}
