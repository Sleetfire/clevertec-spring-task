package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.exception.EssenceNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.service.OrderService;
import ru.clevertec.ecl.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final GiftCertificateService giftCertificateService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, OrderService orderService,
                           GiftCertificateService giftCertificateService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.giftCertificateService = giftCertificateService;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User saved = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return userMapper.toDto(optionalUser.get());
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return userMapper.toDto(optionalUser.get());
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return userMapper.toDto(users);
    }

    @Override
    public PageDto<UserDto> getPage(Pageable pageable) {
        return null;
    }

    @Override
    @Transactional
    public OrderDto makeOrder(CreateOrderDto createOrderDto) {
        UserDto userDto = findByUsername(createOrderDto.getUsername());
        List<GiftCertificateDto> giftCertificates = new ArrayList<>();
        createOrderDto.getCertificateNames().forEach(name -> giftCertificates.add(giftCertificateService.findByName(name)));

        BigDecimal cost = giftCertificates.stream()
                .map(GiftCertificateDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderDto orderDto = OrderDto.builder()
                .user(userDto)
                .certificates(giftCertificates)
                .cost(cost)
                .build();
        return orderService.create(orderDto);
    }
}
