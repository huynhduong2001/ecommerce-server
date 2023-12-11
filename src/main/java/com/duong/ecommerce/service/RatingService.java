package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.ProductException;
import com.duong.ecommerce.model.Rating;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.request.RatingRequest;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
