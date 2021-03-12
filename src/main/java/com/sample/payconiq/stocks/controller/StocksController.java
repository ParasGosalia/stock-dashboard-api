package com.sample.payconiq.stocks.controller;

import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.services.StocksService;
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
        try {
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
        } catch (Exception e) {
            log.error("Error in getAllStocks function {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<List<StockResponse>> getStocksById(@PathVariable("id") long id) {
        StockResponse stockData;
        try {
            log.info("Get Stock by Id API Called");
            stockData = stocksService.getStockById(id);
            return new ResponseEntity<>(Collections.singletonList(stockData), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in getStocksById function {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/stocks")
    public ResponseEntity<StockResponse> addStocks(@RequestBody StockRequest stock) {
        try {
            log.info("Add Stocks API Called");
            return new ResponseEntity<>(stocksService.addStock(stock), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error in addStocks function {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/stocks/{id}")
    public ResponseEntity<StockResponse> updateStocks(@PathVariable("id") long id, @RequestParam("currentPrice") double currentPrice) {

        try {
            log.info("Update Stock API Called");
            StockResponse stockUpdated = stocksService.updateStock(id, currentPrice);
            return new ResponseEntity<>(stockUpdated, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in updateStocks function {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<HttpStatus> deleteStocks(@PathVariable("id") long id) {
        try {
            log.info("Delete Stock API Called");
            stocksService.deleteStockById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error in deleteStocks function {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
