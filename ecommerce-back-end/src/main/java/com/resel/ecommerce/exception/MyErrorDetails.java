package com.resel.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class MyErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
