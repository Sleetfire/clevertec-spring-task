package ru.clevertec.ecl.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.EssenceExistException;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.exception.SqlException;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(EssenceExistException.class)
    public ResponseEntity<SingleResponseError> handleEssenceExistException(EssenceExistException e) {
        return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EssenceNotFoundException.class)
    public ResponseEntity<SingleResponseError> handleEssenceNotFoundException(EssenceNotFoundException e) {
        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SqlException.class)
    public ResponseEntity<SingleResponseError> handleSqlException(SqlException e) {
        return new ResponseEntity<>(e.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
