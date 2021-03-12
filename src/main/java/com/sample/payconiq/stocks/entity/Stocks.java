package com.sample.payconiq.stocks.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STOCKS")
public class Stocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long stockId;

    @Column(name = "STOCK_NAME")
    private String stockName;

    @Column(name = "CURRENT_PRICE")
    private double currentPrice;

    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;


    public Stocks(String name, double currentPrice, LocalDateTime lastUpdate) {
        this.stockName=name;
        this.currentPrice=currentPrice;
        this.lastUpdate=lastUpdate;
    }

}
