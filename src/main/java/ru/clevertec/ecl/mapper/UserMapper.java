package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.repository.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> users);

    List<User> toEntity(List<UserDto> users);

}
