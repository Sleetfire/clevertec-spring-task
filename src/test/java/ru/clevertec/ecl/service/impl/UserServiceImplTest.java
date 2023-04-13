package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void checkCreateShouldReturnUserDto() {
        UserDto userDto = getUserDto();
        User user = getUser();

        doReturn(user)
                .when(userRepository)
                .save(user);

        UserDto fromDb = userService.create(userDto);

        assertThat(fromDb).isEqualTo(userDto);
    }

    @Test
    void checkFindByIdShouldReturnUserDto() {
        UserDto userDto = getUserDto();
        Optional<User> optionalUser = getOptionalUser();
        Long id = userDto.getId();

        doReturn(optionalUser)
                .when(userRepository)
                .findById(id);

        UserDto fromDb = userService.findById(id);

        assertThat(fromDb).isEqualTo(userDto);
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        UserDto userDto = getUserDto();
        Long id = userDto.getId();

        doReturn(Optional.empty())
                .when(userRepository)
                .findById(id);

        assertThatThrownBy(() -> this.userService.findById(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindByUsernameShouldReturnUserDto() {
        UserDto userDto = getUserDto();
        Optional<User> optionalUser = getOptionalUser();
        String username = userDto.getUsername();

        doReturn(optionalUser)
                .when(userRepository)
                .findByUsername(username);

        UserDto fromDb = userService.findByUsername(username);

        assertThat(fromDb).isEqualTo(userDto);
    }

    @Test
    void checkFindByUsernameShouldThrowEssenceNotFoundException() {
        UserDto userDto = getUserDto();
        String username = userDto.getUsername();

        doReturn(Optional.empty())
                .when(userRepository)
                .findByUsername(username);

        assertThatThrownBy(() -> this.userService.findByUsername(username))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllShouldReturnUserDtoList() {
        List<User> userList = getUserList();
        List<UserDto> userDtoList = getUserDtoList();

        doReturn(userList)
                .when(userRepository)
                .findAll();

        List<UserDto> fromDb = userService.findAll();

        assertThat(fromDb).isEqualTo(userDtoList);
    }

    @Test
    void checkFindAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList())
                .when(userRepository)
                .findAll();

        assertThatThrownBy(() -> this.userService.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindPageShouldReturnPageDto() {
        List<User> content = getUserList();
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> page = new PageImpl<>(content, pageable, content.size());

        doReturn(page)
                .when(userRepository)
                .findAll(pageable);

        PageDto<UserDto> pageDto = userService.findPage(pageable);
        assertThat(pageDto.getNumber()).isZero();
        assertThat(pageDto.getSize()).isEqualTo(1);
        assertThat(pageDto.getTotalPages()).isEqualTo(3);
        assertThat(pageDto.getTotalElements()).isEqualTo(3L);
        assertThat(pageDto.isFirst()).isTrue();
        assertThat(pageDto.getNumberOfElements()).isEqualTo(3);
        assertThat(pageDto.isLast()).isFalse();
        assertThat(pageDto.getContent()).isNotEmpty();
    }

    @Test
    void checkFindPageShouldThrowEssenceNotFoundException() {
        Pageable pageable = PageRequest.of(0, 1);
        doReturn(Page.empty())
                .when(userRepository)
                .findAll(pageable);

        assertThatThrownBy(() -> this.userService.findPage(pageable))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void makeOrder() {
    }

    @Test
    void findMostWidelyUsedTagByUsername() {
    }

    User getUser() {
        return User.builder().id(1L).username("user").build();
    }

    UserDto getUserDto() {
        return userMapper.toDto(getUser());
    }

    Optional<User> getOptionalUser() {
        return Optional.of(getUser());
    }

    List<User> getUserList() {
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        User user3 = User.builder().id(3L).username("user3").build();
        return List.of(user1, user2, user3);
    }

    List<UserDto> getUserDtoList() {
        return userMapper.toDto(getUserList());
    }
}