package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateFilter {

    private String tagName;
    private String fieldPart;
    private String sortName;
    private String sortDate;

}
