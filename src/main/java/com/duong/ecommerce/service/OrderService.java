package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.OrderException;
import com.duong.ecommerce.model.Address;
import com.duong.ecommerce.model.Order;
import com.duong.ecommerce.model.User;

import java.util.List;

public interface OrderService{
    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deletedOrder(Long orderId) throws OrderException;
}
