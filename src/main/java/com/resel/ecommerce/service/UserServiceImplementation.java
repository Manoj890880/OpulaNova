package com.resel.ecommerce.service;

import com.resel.ecommerce.config.JwtProvider;
import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public User findUserById(long userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new UserException("No User found with this id -"+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);
        if (user==null){
            throw new UserException("No user found with this email - "+ email);
        }
        return user;
    }
}
