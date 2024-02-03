package com.resel.ecommerce.controller;

import com.resel.ecommerce.exception.OrderException;
import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.Address;
import com.resel.ecommerce.model.Order;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.repository.OrderRepository;
import com.resel.ecommerce.service.OrderService;
import com.resel.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt)
            throws UserException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order =orderService.createOrder(user, shippingAddress);
        System.out.println("Order "+ order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization") String jwt)
            throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
        public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                                   @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }




}
