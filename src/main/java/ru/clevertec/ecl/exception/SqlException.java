package ru.clevertec.ecl.exception;

import ru.clevertec.ecl.dto.SingleResponseError;

public class SqlException extends RuntimeException {

    private SingleResponseError error;

    public SqlException(SingleResponseError error) {
        this.error = error;
    }

    public SqlException() {
        super();
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlException(Throwable cause) {
        super(cause);
    }

    public SingleResponseError getError() {
        return error;
    }
}
