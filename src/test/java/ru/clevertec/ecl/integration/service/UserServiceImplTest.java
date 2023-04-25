package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.CreateOrderDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void checkCreateUserShouldReturnUserDtoWithId() {
        UserDto userDto = UserDto.builder()
                .username("Sasuke")
                .build();

        UserDto createdUserDto = userService.create(userDto);

        assertThat(createdUserDto.getId()).isNotZero();
    }

    @Test
    void checkFindByIdShouldReturnUserDtoWithId1() {
        long expectedUserId = 1L;

        UserDto userDtoFromDb = userService.findById(expectedUserId);

        assertThat(userDtoFromDb.getId()).isEqualTo(expectedUserId);
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        long expectedUserId = 1000L;

        assertThatThrownBy(() -> userService.findById(expectedUserId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindByUsernameShouldReturnUserDtoWithUsernameMadara() {
        String expectedUserName = "Madara";

        UserDto userDtoFromDb = userService.findByUsername(expectedUserName);

        assertThat(userDtoFromDb.getUsername()).isEqualTo(expectedUserName);
    }

    @Test
    void checkFindByUsernameShouldThrowEssenceNotFoundException() {
        String expectedUserName = "Naruto";

        assertThatThrownBy(() -> userService.findByUsername(expectedUserName))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindPageShouldReturnPageDto() {
        int expectedTagsCount = 2;
        Pageable pageable = PageRequest.of(0, 1);

        PageDto<UserDto> page = userService.findPage(pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedTagsCount);
    }

    @Test
    void checkFindAllShouldReturn2() {
        int expectedUsersCount = 2;

        List<UserDto> userDtoList = userService.findAll();

        assertThat(userDtoList).hasSize(expectedUsersCount);
    }

    @Test
    void checkMakeOrderShouldReturnOrderDtoWithId() {
        CreateOrderDto createOrderDto = CreateOrderDto.builder()
                .username("Madara")
                .certificateNames(List.of("pizza"))
                .build();

        OrderDto orderDtoFromDb = userService.makeOrder(createOrderDto);

        assertThat(orderDtoFromDb.getId()).isNotZero();
    }

    @Test
    void checkFindOrdersShouldReturnOrderDtoListWithSize1() {
        int expectedOrderDtoCount = 1;
        Pageable pageable = PageRequest.of(0, 1);
        String username = "Itachi";

        PageDto<OrderDto> page = userService.findOrders(username, pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedOrderDtoCount);
    }

}
