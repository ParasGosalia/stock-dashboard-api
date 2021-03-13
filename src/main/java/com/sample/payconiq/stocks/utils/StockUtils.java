package com.sample.payconiq.stocks.utils;


import com.sample.payconiq.stocks.model.StockRequest;
import org.springframework.util.StringUtils;

public class StockUtils {

    public static boolean validateRequest(StockRequest stockRequest)
    {
        return StringUtils.hasText(stockRequest.getStockName()) && stockRequest.getCurrentPrice() >= 1.00 && stockRequest.getCurrentPrice()<=1000000 ;

    }

    public static boolean validatePrice(double price)
    {
        return price >= 1.00 && price<=1000000 ;
    }
}
