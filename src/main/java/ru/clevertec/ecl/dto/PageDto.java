package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto<T> {

    private int number;
    private int size;
    private int totalPages;
    private int totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private List<T> content;

}
