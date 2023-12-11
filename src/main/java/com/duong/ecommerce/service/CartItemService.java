package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.CartItemException;
import com.duong.ecommerce.exception.UserException;
import com.duong.ecommerce.model.Cart;
import com.duong.ecommerce.model.CartItem;
import com.duong.ecommerce.model.Product;
import com.duong.ecommerce.request.AddItemRequest;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, AddItemRequest req) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
