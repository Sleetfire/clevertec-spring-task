package ru.clevertec.ecl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private long id;
    @JsonIgnore
    private UserDto user;
    @JsonIgnore
    private List<GiftCertificateDto> certificates;
    private BigDecimal cost;
    private String createDate;
    @JsonIgnore
    private OrderStatus status;

}
