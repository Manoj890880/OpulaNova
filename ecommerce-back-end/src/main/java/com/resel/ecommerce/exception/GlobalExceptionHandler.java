package com.resel.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<MyErrorDetails> cartItemExceptionHandler(CartItemException cie, WebRequest req)  {
        MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), cie.getMessage(), req.getDescription(false));
        ResponseEntity<MyErrorDetails> re=new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
        return re;
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<MyErrorDetails> orderExceptionHandler(OrderException oe, WebRequest req)  {
        MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), oe.getMessage(), req.getDescription(false));
        ResponseEntity<MyErrorDetails> re=new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
        return re;
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<MyErrorDetails> orderExceptionHandler(ProductException pe, WebRequest req)  {
        MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), pe.getMessage(), req.getDescription(false));
        ResponseEntity<MyErrorDetails> re=new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
        return re;
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErrorDetails> orderExceptionHandler(UserException ue, WebRequest req)  {
        MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), ue.getMessage(), req.getDescription(false));
        ResponseEntity<MyErrorDetails> re=new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
        return re;
    }



    //to handle generic any type of Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception e, WebRequest req)  {
        MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
