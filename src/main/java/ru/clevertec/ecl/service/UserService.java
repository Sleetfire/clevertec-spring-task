package ru.clevertec.ecl.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CreateOrderDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto findById(Long id);

    UserDto findByUsername(String username);

    List<UserDto> getAll();

    PageDto<UserDto> getPage(Pageable pageable);

    OrderDto makeOrder(CreateOrderDto createOrderDto);

}
