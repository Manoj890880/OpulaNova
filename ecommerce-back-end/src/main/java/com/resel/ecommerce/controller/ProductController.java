package com.resel.ecommerce.controller;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Product;
import com.resel.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;

//    @GetMapping("/products")
//    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
//            @RequestParam String category,
//            @RequestParam List<String> color,
//            @RequestParam List<String> size,
//            @RequestParam Integer minPrice,
//            @RequestParam Integer maxPrice,
//            @RequestParam Integer minDiscount,
//            @RequestParam String sort,
//            @RequestParam String stock,
//            @RequestParam Integer pageNumber,
//            @RequestParam Integer pageSize)
//    {
//        Page<Product> products = productService.getAllProduct(
//                category,
//                color,
//                size,
//                minPrice,
//                maxPrice,
//                minDiscount,
//                sort,
//                stock,
//                pageNumber,
//                pageSize
//        );
//        System.out.println("Complete Products");
//        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
//    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }



}
