package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageDto;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto findById(Long id);

    List<OrderDto> findAll();

    PageDto<OrderDto> findPage(Pageable pageable);

    List<OrderDto> findAllByUsername(String username);

    PageDto<OrderDto> findPageByUsername(String username, Pageable pageable);

}
