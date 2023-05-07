package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderStatus;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.util.DateUtil;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    @InjectMocks
    private OrderServiceImpl orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    @DisplayName("Creating order dto should return created order dto")
    void checkCreateShouldReturnCreatedOrderDto() {
        OrderDto orderDto = getOrderDto();
        Order order = getOrder();

        doReturn(order)
                .when(orderRepository)
                .save(any(Order.class));

        OrderDto saved = orderService.create(orderDto);

        System.out.println(saved);
        assertThat(saved).isNotNull();
    }

    @Test
    void checkFindByIdShouldReturnOrderDto() {
        Optional<Order> optionalOrder = getOptionalOrder();
        OrderDto orderDto = getOrderDto();
        Long id = orderDto.getId();

        doReturn(optionalOrder)
                .when(orderRepository)
                .findById(id);

        OrderDto fromDb = orderService.findById(id);

        assertThat(fromDb).isNotNull();
    }

    @Test
    void checkFindByIdShouldThrowEssenceNotFoundException() {
        OrderDto orderDto = getOrderDto();
        Long id = orderDto.getId();

        doReturn(Optional.empty())
                .when(orderRepository)
                .findById(id);

        assertThatThrownBy(() -> this.orderService.findById(id))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllShouldReturnNotEmptyList() {
        List<Order> orderList = getOrderList();

        doReturn(orderList)
                .when(orderRepository)
                .findAll();

        List<OrderDto> orderDtoList = orderService.findAll();
        assertThat(orderDtoList).isNotEmpty();
    }

    @Test
    void checkFindAllShouldThrowEssenceNotFoundException() {
        doReturn(Collections.emptyList())
                .when(orderRepository)
                .findAll();

        assertThatThrownBy(() -> this.orderService.findAll())
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindAllByUsernameShouldReturnNotEmptyList() {
        List<Order> orderList = getOrderList();
        Order order = getOrder();
        String username = order.getUser().getUsername();

        doReturn(orderList)
                .when(orderRepository)
                .findAllByUsername(username, Sort.by("id").ascending());

        List<OrderDto> orderDtoList = orderService.findAllByUsername(username);
        assertThat(orderDtoList).isNotEmpty();
    }

    @Test
    void checkFindAllByUsernameShouldThrowEssenceNotFoundException() {
        Order order = getOrder();
        String username = order.getUser().getUsername();

        doReturn(Collections.emptyList())
                .when(orderRepository)
                .findAllByUsername(username, Sort.by("id").ascending());

        assertThatThrownBy(() -> this.orderService.findAllByUsername(username))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindPageByUsernameShouldReturnPageDto() {
        List<Order> content = getOrderList();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Order> page = new PageImpl<>(content, pageable, content.size());

        doReturn(page)
                .when(orderRepository)
                .findAll(pageable);

        PageDto<OrderDto> pageDto = orderService.findPage(pageable);
        assertThat(pageDto.getNumber()).isZero();
        assertThat(pageDto.getSize()).isEqualTo(1);
        assertThat(pageDto.getTotalPages()).isEqualTo(1);
        assertThat(pageDto.getTotalElements()).isEqualTo(1L);
        assertThat(pageDto.isFirst()).isTrue();
        assertThat(pageDto.getNumberOfElements()).isEqualTo(1);
        assertThat(pageDto.isLast()).isTrue();
        assertThat(pageDto.getContent()).isNotEmpty();
    }

    @Test
    void checkFindPageByUsernameShouldEssenceNotFoundException() {
        Pageable pageable = PageRequest.of(0, 1);
        doReturn(Page.empty())
                .when(orderRepository)
                .findAll(pageable);

        assertThatThrownBy(() -> this.orderService.findPage(pageable))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    @Test
    void checkFindOrderWithMaxCostByUsernameShouldReturnOrder() {
        List<Order> orders = getOrderList();
        OrderDto orderDto = getOrderDto();
        String username = orderDto.getUser().getUsername();

        doReturn(orders)
                .when(orderRepository)
                .findAllByUsername(username, Sort.by("cost").descending());

        OrderDto fromDb = orderService.findOrderWithMaxCostByUsername(username);

        assertThat(fromDb).isNotNull();
    }

    @Test
    void checkFindOrderWithMaxCostByUsernameShouldThrowEssenceNotFoundException() {
        OrderDto orderDto = getOrderDto();
        String username = orderDto.getUser().getUsername();

        doReturn(Collections.emptyList())
                .when(orderRepository)
                .findAllByUsername(username, Sort.by("cost").descending());

        assertThatThrownBy(() -> this.orderService.findOrderWithMaxCostByUsername(username))
                .isInstanceOf(EssenceNotFoundException.class);
    }

    Order getOrder() {
        Tag tag1 = Tag.builder().id(1L).name("first_tag").build();
        Tag tag2 = Tag.builder().id(2L).name("second_tag").build();
        Tag tag3 = Tag.builder().id(3L).name("third_tag").build();
        GiftCertificate giftCertificate1 = GiftCertificate.builder()
                .id(1L)
                .name("certificate1")
                .description("certificate1")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(List.of(tag2, tag3))
                .build();
        GiftCertificate giftCertificate2 = GiftCertificate.builder()
                .id(1L)
                .name("certificate2")
                .description("certificate2")
                .price(BigDecimal.valueOf(100))
                .duration(Duration.ofDays(10))
                .createDate(DateUtil.getCurrentDateISO8601())
                .lastUpdateDate(DateUtil.getCurrentDateISO8601())
                .tags(List.of(tag1, tag2, tag3))
                .build();

        User user = User.builder()
                .id(1L)
                .username("user1")
                .build();
        return Order.builder()
                .id(1L)
                .user(user)
                .giftCertificates(List.of(giftCertificate1, giftCertificate2))
                .cost(BigDecimal.valueOf(200))
                .createDate(DateUtil.getCurrentDateISO8601())
                .status(OrderStatus.IN_PROCESS)
                .build();
    }

    OrderDto getOrderDto() {
        return orderMapper.toDto(getOrder());
    }

    Optional<Order> getOptionalOrder() {
        return Optional.of(getOrder());
    }

    List<Order> getOrderList() {
        return List.of(getOrder());
    }

    List<OrderDto> getOrderDtoList() {
        return List.of(getOrderDto());
    }
}