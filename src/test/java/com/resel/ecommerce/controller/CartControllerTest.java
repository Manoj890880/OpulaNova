package com.resel.ecommerce.controller;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.Cart;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.request.AddItemRequest;
import com.resel.ecommerce.response.ApiResponse;
import com.resel.ecommerce.service.CartService;
import com.resel.ecommerce.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CartController cartController;

    private final String mockJwt = "mock-jwt";

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserCart_ReturnsCart() throws UserException {
        // Mocking the service response
        Cart mockCart = new Cart();
        Mockito.when(userService.findUserProfileByJwt(mockJwt)).thenReturn(new User());
        Mockito.when(cartService.findUserCart(any())).thenReturn(mockCart);

        // Performing the request
        ResponseEntity<Cart> response = cartController.findUserCart(mockJwt);

        // Asserting the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());
    }


}
