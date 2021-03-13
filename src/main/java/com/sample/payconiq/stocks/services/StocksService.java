package com.sample.payconiq.stocks.services;

import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StocksService {

    List<StockResponse> getAllStocks();
    StockResponse getStockById(long id) ;
    StockResponse updateStock(long id, double currentPrice);
    StockResponse addStock(StockRequest stock);
    void deleteStockById(long id);


}
