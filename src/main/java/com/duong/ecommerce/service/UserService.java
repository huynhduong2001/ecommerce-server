package com.duong.ecommerce.service;

import com.duong.ecommerce.exception.UserException;
import com.duong.ecommerce.model.User;


public interface UserService {
    public User findUserById(Long id) throws UserException;
    public User findUserProfileByJwt(String jwt) throws UserException;
}
