package ru.clevertec.ecl.mapper;


import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.repository.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);

    List<OrderDto> toDto(List<Order> orders);

    List<Order> toEntity(List<OrderDto> orders);

}
