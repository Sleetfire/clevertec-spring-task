package ru.clevertec.ecl.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private long id;
    private String name;
}
