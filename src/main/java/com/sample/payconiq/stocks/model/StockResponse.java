package com.sample.payconiq.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockResponse {

    private long stockId;
    private String stockName;
    private double currentPrice;
    private String lastUpdate;


}
