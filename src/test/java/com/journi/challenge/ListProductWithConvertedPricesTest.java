package com.journi.challenge;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc


public class ListProductWithConvertedPricesTest {
    @Autowired
    private MockMvc mockMvc;

    /** Test Case for Product Listing with currency code and price conversion - BR **/
    @Test
    public void ListProductsWithCurrencyCodeAndConvertedPriceBR() throws Exception {
        mockMvc.perform(get("/products?countryCode=BR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", IsEqual.equalTo(4)));
    }


    /** Test Case for Product Listing with currency code and price conversion - US **/
    @Test
    public void ListProductsWithCurrencyCodeAndConvertedPriceUS() throws Exception {
        mockMvc.perform(get("/products?countryCode=US"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", IsEqual.equalTo(4)));
    }

}
