package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageDto;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto getById(Long id);

    List<OrderDto> getAll();

    PageDto<OrderDto> getPage(Pageable pageable);

}
