package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.OrderStatus;
import ru.clevertec.ecl.dto.PageDto;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.util.DateUtil;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        orderDto.setCreateDate(DateUtil.getCurrentDateISO8601());
        orderDto.setStatus(OrderStatus.IN_PROCESS);
        Order saved = orderRepository.save(orderMapper.toEntity(orderDto));
        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDto getById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(optionalOrder.get());
    }

    @Override
    public List<OrderDto> getAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(orders);
    }

    @Override
    public PageDto<OrderDto> getPage(Pageable pageable) {
        return null;
    }
}
