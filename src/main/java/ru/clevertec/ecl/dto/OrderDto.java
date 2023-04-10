package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private long id;
    private UserDto user;
    private List<GiftCertificateDto> certificates;
    private BigDecimal cost;
    private String createDate;
    private OrderStatus status;

}
