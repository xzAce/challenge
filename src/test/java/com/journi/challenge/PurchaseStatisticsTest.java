package com.journi.challenge;

import com.journi.challenge.controllers.PurchasesController;
import com.journi.challenge.models.Purchase;
import com.journi.challenge.models.PurchaseStats;
import com.journi.challenge.repositories.PurchasesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseStatisticsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchasesController purchasesController;
    @Autowired
    private PurchasesRepository purchasesRepository;

    @Test
    public void testPurchaseStatistics() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDate = now.minusDays(20);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        // Inside window purchases
        purchasesRepository.save(new Purchase("1", firstDate, Collections.emptyList(), "Arthur", 21.94, "USD"));
        purchasesRepository.save(new Purchase("1", firstDate.plusDays(1), Collections.emptyList(), "Everton", 226.61, "BRL"));
        purchasesRepository.save(new Purchase("1", firstDate.plusDays(2), Collections.emptyList(), "Bican", 120.0, "EUR"));
        purchasesRepository.save(new Purchase("1", firstDate.plusDays(3), Collections.emptyList(), "Hans", 25.0, "EUR"));

        // Outside window purchases
        purchasesRepository.save(new Purchase("1", now.minusDays(31), Collections.emptyList(), "Arthur", 10.0, "USD"));
        purchasesRepository.save(new Purchase("1", now.minusDays(31), Collections.emptyList(), "Everton", 220.0, "BRL"));
        purchasesRepository.save(new Purchase("1", now.minusDays(32), Collections.emptyList(), "Bican", 120.0, "HU"));
        purchasesRepository.save(new Purchase("1", now.minusDays(33), Collections.emptyList(), "Hans", 25.5, "DE"));



        PurchaseStats purchaseStats = purchasesController.getStats();
        assertEquals(formatter.format(firstDate).toString(), purchaseStats.getFrom());
        assertEquals(formatter.format(firstDate.plusDays(3)), purchaseStats.getTo());
        assertEquals(4, purchaseStats.getCountPurchases());
        assertEquals(205, purchaseStats.getTotalAmount());
        assertEquals(51.25, purchaseStats.getAvgAmount());
        assertEquals(25.0, purchaseStats.getMinAmount());
        assertEquals(120.0, purchaseStats.getMaxAmount());
    }
}
