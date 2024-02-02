package com.resel.ecommerce.service;

import com.resel.ecommerce.exception.ProductException;
import com.resel.ecommerce.model.Category;
import com.resel.ecommerce.model.Product;
import com.resel.ecommerce.repository.CategoryRepository;
import com.resel.ecommerce.repository.ProductRepository;
import com.resel.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements  ProductService{

    private ProductRepository productRepository;

    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImplementation(ProductRepository productRepository,
                                        UserService userService,
                                        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel==null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);


            topLevel = categoryRepository.save(topLevelCategory);
        }

        Category secondLevel = categoryRepository.findByNameAndParant(req.getSecondLevelCategory(),topLevel.getName());

        if (secondLevel==null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParant(req.getThirdLevelCategory(),secondLevel.getName());
        if (thirdLevel==null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPresent(req.getDiscountPersent());

        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());

        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);

        product.setCreatedAt(LocalDateTime.now());


        Product savedProduct = productRepository.save(product);



        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        if (req.getTitle() != null) {
            product.setTitle(req.getTitle());
        }
        if (req.getDescription() != null) {
            product.setDescription(req.getDescription());
        }
        if (req.getPrice() != 0) {
            product.setPrice(req.getPrice());
        }
        if (req.getDiscountedPrice() != 0) {
            product.setDiscountedPrice(req.getDiscountedPrice());
        }
        if (req.getDiscountedPresent() != 0) {
            product.setDiscountedPresent(req.getDiscountedPresent());
        }
        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }
        if (req.getBrand() != null) {
            product.setBrand(req.getBrand());
        }
        if (req.getColor() != null) {
            product.setColor(req.getColor());
        }
        // Add more fields as needed

        return productRepository.save(product);

    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt =productRepository.findById(productId);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with the Id -"+productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        return null;
    }

//    @Override
//    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
//        Pageable pageable= PageRequest.of(pageNumber,pageSize);
//        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
//
//        if (!colors.isEmpty()){
//            products = products.stream().filter(p->colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
//        }
//
//        if (stock!=null){
//            if (stock.equals("in_stock")){
//                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
//            }
//
//            else if (stock.equals("out_of_stock")){
//                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
//            }
//        }
//
//        int startIndex = (int) pageable.getOffset();
//        int endIndex =Math.min(startIndex + pageable.getPageSize(),products.size());
//
//        List<Product> pageContent = products.subList(startIndex, endIndex);
//
//        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,pageSize);
//
//        return filteredProducts;
//    }
}
