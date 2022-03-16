package com.journi.challenge.repositories;

import com.journi.challenge.CurrencyConverter;
import com.journi.challenge.models.Purchase;
import com.journi.challenge.models.PurchaseStats;

import javax.inject.Named;
import javax.inject.Singleton;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Named
@Singleton
public class PurchasesRepository {

    private final List<Purchase> allPurchases = new ArrayList<>();

    public List<Purchase> list() {
        return allPurchases;
    }

    public void save(Purchase purchase) {
        String currencyCode;
        double price;
        double convertedPrice;

        currencyCode = purchase.getCurrencyCode();
        price = purchase.getTotalValue();

        if (!currencyCode.equalsIgnoreCase("EUR")) {
            CurrencyConverter currencyConverter = new CurrencyConverter();
            convertedPrice = currencyConverter.convertCurrencyToEur(currencyCode, price);
            purchase.setCurrencyCode("EUR");
            purchase.setTotalValue(convertedPrice);
        }

        allPurchases.add(purchase);
    }

    public PurchaseStats getLast30DaysStats() {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE.withZone(ZoneId.of("UTC"));

        LocalDateTime start = LocalDate.now().atStartOfDay().minusDays(30);

        List<Purchase> recentPurchases = allPurchases
                .stream()
                .filter(p -> p.getTimestamp().isAfter(start))
                /** Sort by total Value from Purchase instead of timedate**/
                .sorted(Comparator.comparing(Purchase::getTotalValue))
                .collect(Collectors.toList());

        long countPurchases = recentPurchases.size();
        double totalAmountPurchases = recentPurchases.stream().mapToDouble(Purchase::getTotalValue).sum();
        return new PurchaseStats(
                formatter.format(start),
                formatter.format(LocalDate.now()),
                countPurchases,
                totalAmountPurchases,
                totalAmountPurchases / countPurchases,
                /**Obtaining Max and Min value directly through list index, instead of iterating list again**/
                recentPurchases.get(0).getTotalValue(),
                recentPurchases.get(recentPurchases.size()-1).getTotalValue()

        );
    }
}
