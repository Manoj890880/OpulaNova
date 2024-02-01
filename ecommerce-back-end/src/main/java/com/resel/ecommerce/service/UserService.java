package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.User;

public interface UserService {
    public User findUserById(long userId) throws UserException;


    public User findUserProfileByJwt(String jwt) throws UserException;


}
