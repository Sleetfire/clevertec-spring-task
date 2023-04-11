package ru.clevertec.ecl.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.exception.IllegalRequestParamException;
import ru.clevertec.ecl.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.create(userDto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/orders",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<OrderDto> makeOrder(@RequestBody CreateOrderDto createOrderDto) {
        return new ResponseEntity<>(userService.makeOrder(createOrderDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/orders/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageDto<OrderDto>> findOrders(@PathVariable String username,
                                                        @RequestParam(defaultValue = "0", required = false) int page,
                                                        @RequestParam(defaultValue = "1", required = false) int size) {
        if (page < 0) {
            throw new IllegalRequestParamException(40001);
        }
        if (size < 1) {
            throw new IllegalRequestParamException(40001);
        }
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(userService.findOrders(username, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/orders/tags/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TagDto> getMostWidelyUsedTagByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findMostWidelyUsedTagByUsername(username), HttpStatus.OK);
    }

}
