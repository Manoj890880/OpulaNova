package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Rating;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingService {

    public Rating createRating(RatingRequest re, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
