package io.isiflix.isispring.controller;

import io.isiflix.isispring.annotation.IsiBody;
import io.isiflix.isispring.annotation.IsiController;
import io.isiflix.isispring.annotation.IsiGetMethod;
import io.isiflix.isispring.annotation.IsiPostMethod;
import io.isiflix.isispring.entity.Product;

import java.math.BigDecimal;

@IsiController
public class HelloController {

    @IsiGetMethod("/hello")
    public String sayHelloWorld(){
        return "Hello World!";
    }

    @IsiGetMethod("/test")
    public String dayTest(){
        return "Teste funcionando";
    }

    @IsiGetMethod("/product")
    public Product getProduct(){
        var product = new Product();
        product.setId(1L);
        product.setName("Sabonete");
        product.setPrice(new BigDecimal("10.00"));
        return product;
    }

    @IsiPostMethod("/product")
    public String createProduct(@IsiBody Product product){
        System.out.println();
        return "Produto cadastrado";
    }

}
