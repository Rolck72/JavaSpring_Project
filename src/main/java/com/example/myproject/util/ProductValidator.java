package com.example.myproject.util;


import com.example.myproject.models.Product;
import com.example.myproject.services.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator{

    private final ProductService productService;

    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }


    // в данном методе указываем для какой модели предназначен данный валидатор
    @Override
    public boolean supports(Class<?> clazz) {

        return Product.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if(productService.getProductFindByTitle(product) !=null){
            errors.rejectValue("title", "", "Данное наименование товара уже используется");
        }
    }
}
