package com.journi.challenge.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Represents a completed Purchase.
 * invoiceNumber is unique
 * timestamp when the purchase was made. Epoch milliseconds
 * productIds list of product ids included in this purchase
 * customerName name of the customer
 * totalValue total value of this purchase, in EUR
 */


/** Added currencyCode variable to Purchase**/
public class Purchase {

    private final String invoiceNumber;
    private final LocalDateTime timestamp;
    private final List<String> productIds;
    private final String customerName;
    private Double totalValue;
    private String currencyCode;

    public Purchase(String invoiceNumber, LocalDateTime timestamp, List<String> productIds, String customerName, Double totalValue, String currencyCode) {
        this.invoiceNumber = invoiceNumber;
        this.timestamp = timestamp;
        this.productIds = productIds;
        this.customerName = customerName;
        this.totalValue = totalValue;
        this.currencyCode = currencyCode;
    }


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setTotalValue(double value){

        this.totalValue = value;
    }

    public void setCurrencyCode(String currencyCode){

        this.currencyCode = currencyCode;
    }


}
