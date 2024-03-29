package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Product;
import com.resel.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId,Product product) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;

    public List<Product> findProductByCategory(String category) ;

//    public Page<Product> getAllProduct(String category,List<String> colors,List<String>sizes,Integer minPrice,Integer maxPrice
//    ,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize) ;

}
