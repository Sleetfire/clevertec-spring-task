package ru.clevertec.ecl.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateEntity {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Duration duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagEntity> tags;

}
