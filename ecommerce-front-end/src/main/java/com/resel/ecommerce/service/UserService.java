package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    public User findUserBy(long userId) throws UserException;


    public User findUserProfileByJwt(String jwt) throws UserException;


}
