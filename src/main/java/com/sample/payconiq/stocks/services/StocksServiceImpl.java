package com.sample.payconiq.stocks.services;

import com.sample.payconiq.stocks.entity.Stocks;
import com.sample.payconiq.stocks.exception.StockNotFoundException;
import com.sample.payconiq.stocks.model.StockRequest;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.repository.StocksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static com.sample.payconiq.stocks.utils.Constants.DATE_FORMAT;

@RequiredArgsConstructor
@Slf4j
@Component
public class StocksServiceImpl implements StocksService {


    private final StocksRepository stocksRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public List<StockResponse> getAllStocks() {
        log.debug("Retrieving all Stocks");
        return stocksRepository.findAll().stream()
                .map(StocksServiceImpl::mapToResponseDTO)
                .sorted(Comparator.comparing(StockResponse::getLastUpdate).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public StockResponse getStockById(long id) {
        log.debug("Retrieving data for Stock id {}", id);
        Stocks stock = stocksRepository.findById(id).orElseThrow(() -> new StockNotFoundException("Stock Id not found"));
        return mapToResponseDTO(stock);
    }

    public StockResponse updateStock(long id, double stockPrice) throws Exception {
        log.debug("Updating Stock id {} with price {}", id, stockPrice);
        Stocks stocks = stocksRepository.findById(id)
                .map(stockUpdated -> {
                    stockUpdated.setCurrentPrice(stockPrice);
                    stockUpdated.setLastUpdate(LocalDateTime.now());
                    return stocksRepository.save(stockUpdated);
                })
                .orElseThrow(() -> new StockNotFoundException("Stock Id not found"));

        return mapToResponseDTO(stocks);

    }

    public StockResponse addStock(StockRequest stock) {
        Stocks stocks = stocksRepository.findByStockName(stock.getStockName());
        if (stocks!=null) {
            stocks.setCurrentPrice(stock.getCurrentPrice());
            stocks.setLastUpdate(LocalDateTime.now());
            log.debug("Stock with name {} already present so updated its currentPrice to {}", stock.getStockName(), stock.getCurrentPrice());
            return mapToResponseDTO(stocksRepository.save(stocks));

        } else {
            Stocks addedStock = stocksRepository
                    .save(new Stocks(stock.getStockName(), stock.getCurrentPrice(), LocalDateTime.now()));
            log.debug("Stock with name {} and price {} saved successfully", stock.getStockName(), stock.getCurrentPrice());
            return mapToResponseDTO(addedStock);
        }
    }

    public void deleteStockById(long id) {
        stocksRepository.deleteById(id);
        log.debug("Stock with id {} deleted successfully", id);
    }

    private static StockResponse mapToResponseDTO(Stocks stock) {

        return new StockResponse(stock.getStockId(), stock.getStockName(), stock.getCurrentPrice(), formatter.format(stock.getLastUpdate()));

    }


}
