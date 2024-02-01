package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Cart;
import com.resel.ecommerce.model.CartItem;
import com.resel.ecommerce.model.Product;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.repository.CartRepository;
import com.resel.ecommerce.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart,product,req.getSize(),userId);

        if (isPresent==null){
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);

                cartItem.setQuantity(req.getQuantity());
                cartItem.setUserId(userId);

                int price=req.getQuantity()* product.getDiscountedPrice();

                cartItem.setPrice(price);

                cartItem.setSize(req.getSize());

                CartItem createdCartItem = cartItemService.createCartItem(cartItem);

                cart.getCartItems().add(createdCartItem);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice= 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;


        for (CartItem cartItem : cart.getCartItems()) {
            System.out.println(cartItem);
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);

        cart.setDiscounte(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
