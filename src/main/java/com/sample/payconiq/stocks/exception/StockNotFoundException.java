package com.sample.payconiq.stocks.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
