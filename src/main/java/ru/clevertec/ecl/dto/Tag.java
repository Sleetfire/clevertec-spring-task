package ru.clevertec.ecl.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    private long id;
    private String name;
}
