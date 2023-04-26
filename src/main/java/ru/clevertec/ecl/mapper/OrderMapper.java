package ru.clevertec.ecl.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.repository.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Mapping(source = "order.giftCertificates", target = "certificates")
    OrderDto toDto(Order order);

    @Mapping(source = "orderDto.certificates", target = "giftCertificates")
    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDto(List<Order> orders);

    List<Order> toEntity(List<OrderDto> orders);

}
