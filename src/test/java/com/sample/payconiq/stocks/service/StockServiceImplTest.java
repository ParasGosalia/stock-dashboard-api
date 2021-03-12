package com.sample.payconiq.stocks.service;

import com.sample.payconiq.stocks.entity.Stocks;
import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.repository.StocksRepository;
import com.sample.payconiq.stocks.services.StocksService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { StocksService.class })
public class StockServiceImplTest {

    @MockBean
    StocksService stocksService;

    @MockBean(StocksRepository.class)
    StocksRepository stocksRepository;


    @Test
    public void getAllStocksTest()
    {
        StockRequest stock = new StockRequest("GOOGLE",300);
        Stocks stocks = new Stocks(1,"GOOGLE",300, LocalDateTime.now());
        List<Stocks> results = new ArrayList<>();
        results.add(stocks);
        when(
                stocksRepository.findAll()
        ).thenReturn(results);
        List<StockResponse> actualResult = stocksService.getAllStocks();
        Mockito.verify(stocksService, Mockito.times(1)).getAllStocks();
        assertNotNull(actualResult);

    }




}
