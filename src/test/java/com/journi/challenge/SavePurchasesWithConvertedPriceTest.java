package com.journi.challenge;

import com.journi.challenge.controllers.PurchasesController;
import com.journi.challenge.models.Purchase;
import com.journi.challenge.repositories.PurchasesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Added test cases to test saving purchases with converted Prices**/
@SpringBootTest
@AutoConfigureMockMvc
public class SavePurchasesWithConvertedPriceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchasesController purchasesController;
    @Autowired
    private PurchasesRepository purchasesRepository;

    private String getPurchaseJson(String invoiceNumber, String customerName, String dateTime, Double totalValue, String currencyCode, String... productIds) {
        String productIdList = "[\"" + String.join("\",\"", productIds) + "\"]";
        return String.format(Locale.US,"{\"invoiceNumber\":\"%s\",\"customerName\":\"%s\",\"dateTime\":\"%s\",\"productIds\":%s,\"amount\":%.2f,\"currencyCode\":\"%s\"}", invoiceNumber, customerName, dateTime, productIdList, totalValue, currencyCode);
    }

    @Test
    public void testPurchaseCurrencyCodeUS() throws Exception {
        String body = getPurchaseJson("1", "Arthur", "2020-03-14T10:00:00+01:00", 30.25, "USD", "product1");
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON).content(body)
        ).andExpect(status().isOk());

        Purchase savedPurchase = purchasesRepository.list().get(purchasesRepository.list().size() - 1);
        assertEquals("Arthur", savedPurchase.getCustomerName());
        assertEquals("1", savedPurchase.getInvoiceNumber());
        assertEquals("2020-03-14T10:00:00", savedPurchase.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(30.25, savedPurchase.getTotalValue());
        assertEquals("USD", savedPurchase.getCurrencyCode());
    }


    @Test
    public void testPurchaseCurrencyCodeBR() throws Exception {
        String body = getPurchaseJson("1", "Everton", "2020-03-14T10:00:00+01:00", 200.25, "BRL", "product2");
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON).content(body)
        ).andExpect(status().isOk());

        Purchase savedPurchase = purchasesRepository.list().get(purchasesRepository.list().size() - 1);
        assertEquals("Everton", savedPurchase.getCustomerName());
        assertEquals("1", savedPurchase.getInvoiceNumber());
        assertEquals("2020-03-14T10:00:00", savedPurchase.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));
        assertEquals(200.25, savedPurchase.getTotalValue());
        assertEquals("BRL", savedPurchase.getCurrencyCode());
    }
}
