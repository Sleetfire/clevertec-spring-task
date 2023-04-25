package ru.clevertec.ecl.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.service.OrderService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    void checkCreateShouldReturnOrderDto() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("Itachi")
                .build();
        GiftCertificateDto giftCertificateDto = GiftCertificateDto.builder()
                .id(1L)
                .name("barber-shop")
                .description("good haircut for real men")
                .price(BigDecimal.valueOf(25.0))
                .duration(Duration.of(10, ChronoUnit.DAYS))
                .createDate("2023-04-24T19:14:35.559")
                .lastUpdateDate("2023-04-24T19:14:35.559")
                .build();
        OrderDto orderDto = OrderDto.builder()
                .user(userDto)
                .certificates(List.of(giftCertificateDto))
                .cost(giftCertificateDto.getPrice())
                .build();

        OrderDto orderDtoFromDb = orderService.create(orderDto);

        assertThat(orderDtoFromDb.getId()).isNotZero();
    }

    @Test
    void checkFindByIdShouldReturnOrderDtoWithId1() {
        long orderDtoId = 1L;

        OrderDto orderDtoFromDb = orderService.findById(orderDtoId);

        assertThat(orderDtoFromDb.getId()).isEqualTo(orderDtoId);
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        long orderDtoId = 100L;

        assertThatThrownBy(() -> orderService.findById(orderDtoId))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllShouldReturnOrderDtoListWithSize2() {
        int expectedOrderDtoCount = 2;

        List<OrderDto> orderDtoList = orderService.findAll();

        assertThat(orderDtoList).hasSize(expectedOrderDtoCount);
    }

    @Test
    void checkFindAllByUsernameShouldReturnOrderDtoListWithSize1() {
        int expectedOrderDtoCount = 1;
        String username = "Itachi";

        List<OrderDto> orderDtoList = orderService.findAllByUsername(username);

        assertThat(orderDtoList).hasSize(expectedOrderDtoCount);
    }

    @Test
    void checkFindPageShouldReturnOrderDtoPageWithSize2() {
        int expectedOrderDtoCount = 2;
        Pageable pageable = PageRequest.of(0, 1);

        PageDto<OrderDto> page = orderService.findPage(pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedOrderDtoCount);
    }

    @Test
    void checkFindPageByUsernameShouldReturnOrderDtoPageWithSize1() {
        int expectedOrderDtoCount = 1;
        Pageable pageable = PageRequest.of(0, 1);
        String username = "Itachi";

        PageDto<OrderDto> page = orderService.findPageByUsername(username, pageable);

        assertThat(page.getTotalElements()).isEqualTo(expectedOrderDtoCount);
    }

    @Test
    void checkFindOrderWithMaxCostByUsernameShouldReturnOrder() {
        String username = "Madara";

        OrderDto orderDtoFromDb = orderService.findOrderWithMaxCostByUsername(username);

        assertThat(orderDtoFromDb.getCost()).isEqualByComparingTo(BigDecimal.valueOf(58.98));
    }

}
