package com.resel.ecommerce.controller;


import com.resel.ecommerce.config.JwtProvider;
import com.resel.ecommerce.exception.UserException;
import com.resel.ecommerce.model.Cart;
import com.resel.ecommerce.model.User;
import com.resel.ecommerce.repository.UserRepository;
import com.resel.ecommerce.request.LoginRequest;
import com.resel.ecommerce.response.AuthResponse;
import com.resel.ecommerce.service.CartService;
import com.resel.ecommerce.service.CustomUserServiceImplementation;
import com.resel.ecommerce.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {


    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder;

    private CustomUserServiceImplementation customUserServiceImplementation;
    private CartService cartService;

    @Autowired
    private TwilioService twilioService;


    public AuthController(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CustomUserServiceImplementation customUserServiceImplementation, CartService cartService) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserServiceImplementation = customUserServiceImplementation;
        this.cartService = cartService;
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        String userPhoneNumber = user.getMobile();

        User isEmailExists = userRepository.findByEmail(email);
        if (isEmailExists != null) {
            throw new UserException("Email already exists");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setMobile(userPhoneNumber);
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMassage("Signup success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) throws UserException {
        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        Authentication authentication = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMassage("Signin success");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");

        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }
}
