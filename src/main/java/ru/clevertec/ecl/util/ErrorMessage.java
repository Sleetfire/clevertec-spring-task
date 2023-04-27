package ru.clevertec.ecl.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {

    public static final String TAG_EXISTING_ERROR = "Tag with that name is already existing";
    public static final String NOT_FOUND_ERROR = "Requested resource was not found";
    public static final String NOT_FOUND_WITH_PARAMS = "Requested resource with current params was not found";

}
