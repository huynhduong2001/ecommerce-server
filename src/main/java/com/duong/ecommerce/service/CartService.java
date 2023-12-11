package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.ProductException;
import com.duong.ecommerce.model.Cart;
import com.duong.ecommerce.model.User;
import com.duong.ecommerce.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long id);
}
