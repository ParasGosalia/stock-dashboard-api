package com.sample.payconiq.stocks.repository;

import com.sample.payconiq.stocks.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

    Stocks findByStockName(String stockName);
}
