package ru.clevertec.ecl.service.impl;

import org.springframework.data.domain.Page;
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
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.service.UserService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, OrderService orderService,
                           GiftCertificateService giftCertificateService, TagService tagService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
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
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EssenceNotFoundException(40401);
        }
        return userMapper.toDto(users);
    }

    @Override
    public PageDto<UserDto> findPage(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return convertToPageDto(page);
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

    @Override
    public PageDto<OrderDto> findOrders(String username, Pageable pageable) {
        return orderService.findPageByUsername(username, pageable);
    }

    @Override
    public TagDto findMostWidelyUsedTagByUsername(String username) {
        OrderDto orderDto = orderService.findOrderWithMaxCostByUsername(username);
        List<GiftCertificateDto> certificates = orderDto.getCertificates();
        Map<String, List<TagDto>> collect = certificates.stream()
                .flatMap(giftCertificateDto -> giftCertificateDto.getTags().stream())
                .collect(Collectors.groupingBy(TagDto::getName));
        Map<TagDto, Integer> resultMap = new HashMap<>();
        collect.forEach((name, tags) -> resultMap.put(tagService.findByName(name), tags.size()));

        Optional<TagDto> result = resultMap.entrySet()
                .stream()
                .sorted()
                .map(Map.Entry::getKey)
                .findFirst();
        if (result.isEmpty()) {
            throw new RuntimeException();
        }
        return result.get();
    }

    private PageDto<UserDto> convertToPageDto(Page<User> page) {
        List<User> content = page.getContent();
        if (content.isEmpty()) {
            throw new EssenceNotFoundException(40402);
        }
        return PageDto.Builder.createBuilder(UserDto.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements())
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(userMapper.toDto(content))
                .build();
    }
}
