package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        orderDto.setCreateDate(DateUtil.getCurrentDateISO8601());
        orderDto.setStatus(OrderStatus.IN_PROCESS);
        Order saved = orderRepository.save(orderMapper.toEntity(orderDto));
        return orderMapper.toDto(saved);
    }

    @Override
    public OrderDto findById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(optionalOrder.get());
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(orders);
    }

    @Override
    public PageDto<OrderDto> findPage(Pageable pageable) {
        Page<Order> page = orderRepository.findAll(pageable);
        return convertToPageDto(page);
    }

    @Override
    public List<OrderDto> findAllByUsername(String username) {
        List<Order> orders = orderRepository.findAllByUsername(username, Sort.by("id").ascending());
        if (orders.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(orders);
    }

    @Override
    public PageDto<OrderDto> findPageByUsername(String username, Pageable pageable) {
        Page<Order> page = orderRepository.findAllByUsername(username, pageable);
        return convertToPageDto(page);
    }

    @Override
    public OrderDto findOrderWithMaxCostByUsername(String username) {
        List<Order> orders = orderRepository.findAllByUsername(username, Sort.by("cost").descending());
        if (orders.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return orderMapper.toDto(orders.get(0));
    }

    private PageDto<OrderDto> convertToPageDto(Page<Order> page) {
        List<Order> content = page.getContent();
        if (content.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return PageDto.Builder.createBuilder(OrderDto.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(orderMapper.toDto(content))
                .build();
    }
}
