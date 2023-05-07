package ru.clevertec.ecl.exception;

import ru.clevertec.ecl.dto.SingleResponseError;

public class SqlException extends RuntimeException {

    private final int errorCode;

    public SqlException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return "Bad request to the database";
    }
}
