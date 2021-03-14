package com.sample.payconiq.stocks.controller;

import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.services.StocksService;
import com.sample.payconiq.stocks.utils.StockUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api")
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
            }
            else {
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
    public ResponseEntity<?> addStocks(@RequestBody StockRequest stock) {
            log.info("Add Stocks API Called");
            if(StockUtils.validateRequest(stock)) {
                return new ResponseEntity<>(stocksService.addStock(stock), HttpStatus.CREATED);
            } else
            {
                return new ResponseEntity<>("Please enter a valid Input", HttpStatus.BAD_REQUEST);
            }
    }


    @PutMapping("/stocks/{id}")
    public ResponseEntity<?> updateStocks(@PathVariable("id") long id, @RequestParam("currentPrice") double currentPrice) {
            log.info("Update Stock API Called");
            if(StockUtils.validatePrice(currentPrice)) {
                return new ResponseEntity<>(stocksService.updateStock(id, currentPrice), HttpStatus.OK);
            }else
            {
                return new ResponseEntity<>("Please enter a valid Stock Price", HttpStatus.BAD_REQUEST);
            }

    }


    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<HttpStatus> deleteStocks(@PathVariable("id") long id) {
            log.info("Delete Stock API Called");
            stocksService.deleteStockById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }



}
