package com.journi.challenge.repositories;

import com.journi.challenge.CurrencyConverter;
import com.journi.challenge.models.Product;
import org.springframework.stereotype.Component;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Named
@Singleton
public class ProductsRepository {


    private List<Product> allProducts = new ArrayList<>();


    public List<Product> list(String countryCode) {
        /** Obtain Currency Code through CountryCode**/
        CurrencyConverter currencyConverter = new CurrencyConverter();
        String currencyCode;
        currencyCode= currencyConverter.getCurrencyForCountryCode(countryCode);

        allProducts.add(new Product("photobook-square-soft-cover", "Photobook Square with Soft Cover", getProductsConvertedPrice(25.0, currencyCode ), currencyCode));
        allProducts.add(new Product("photobook-square-hard-cover", "Photobook Square with Hard Cover", getProductsConvertedPrice(30.0, currencyCode ), currencyCode));
        allProducts.add(new Product("photobook-landscape-soft-cover", "Photobook Landscape with Soft Cover", getProductsConvertedPrice(35.0, currencyCode ), currencyCode));
        allProducts.add(new Product("photobook-landscape-hard-cover", "Photobook Landscape with Hard Cover", getProductsConvertedPrice(45.0, currencyCode ), currencyCode));
        return allProducts;
    }

    /** Getting a Product's Converted price according to currencyCode of given countryCode**/

    private double getProductsConvertedPrice(double defaultPrice, String currencyCode){
        CurrencyConverter currencyConverter = new CurrencyConverter();
        double price;
        price = currencyConverter.convertEurToCurrency(currencyCode, defaultPrice );

        return price;
    }

}
