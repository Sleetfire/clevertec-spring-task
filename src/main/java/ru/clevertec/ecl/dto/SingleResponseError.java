package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleResponseError {

    private String errorMessage;
    private int errorCode;

    public static SingleResponseError of(String errorMessage, int errorCode) {
        return new SingleResponseError(errorMessage, errorCode);
    }
}
