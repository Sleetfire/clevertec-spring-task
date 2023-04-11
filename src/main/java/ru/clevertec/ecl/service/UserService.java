package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.*;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    List<UserDto> findAll();

    PageDto<UserDto> findPage(Pageable pageable);

    OrderDto makeOrder(CreateOrderDto createOrderDto);

    PageDto<OrderDto> findOrders(String username, Pageable pageable);

    TagDto findMostWidelyUsedTagByUsername(String username);

}
