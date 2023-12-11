package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.ProductException;
import com.duong.ecommerce.model.Review;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
