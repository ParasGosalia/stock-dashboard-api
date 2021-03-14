package com.sample.payconiq.stocks.controller;

import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.services.StocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.util.Collections;
import java.util.List;

import static com.sample.payconiq.stocks.utils.Constants.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api")
@Validated
public class StocksController {


    private final StocksService stocksService;

    @GetMapping("/stocks")
    public ResponseEntity<List<StockResponse>> getAllStocks() {
        log.info("Get All Stocks API Called");
        List<StockResponse> stocks;
        stocks = stocksService.getAllStocks();
        if (stocks.isEmpty()) {
            log.warn("No stocks found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(stocks, HttpStatus.OK);
        }
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<StockResponse>> getStocksById(@PathVariable("id") long id) {
        StockResponse stockData;
        log.info("Get Stock by Id API Called");
        stockData = stocksService.getStockById(id);
        return new ResponseEntity<>(Collections.singletonList(stockData), HttpStatus.OK);

    }

    @PostMapping("/stocks")
    public ResponseEntity<StockResponse> addStocks(@Valid @RequestBody StockRequest stock) {
        log.info("Add Stocks API Called");
        return new ResponseEntity<>(stocksService.addStock(stock), HttpStatus.CREATED);
    }


    @PutMapping("/stocks/{id}")
    public ResponseEntity<StockResponse> updateStocks(@PathVariable("id") long id, @RequestParam("currentPrice") @DecimalMin(message = DECIMAL_MIN_MESSAGE, value = DECIMAL_MIN_VALUE)
    @DecimalMax(value = DECIMAL_MAX_VALUE, message = DECIMAL_MAX_MESSAGE) double currentPrice) {
        log.info("Update Stock API Called");
        return new ResponseEntity<>(stocksService.updateStock(id, currentPrice), HttpStatus.OK);
    }


    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<HttpStatus> deleteStocks(@PathVariable("id") long id) {
        log.info("Delete Stock API Called");
        stocksService.deleteStockById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
