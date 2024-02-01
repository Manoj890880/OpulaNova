package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Cart;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
