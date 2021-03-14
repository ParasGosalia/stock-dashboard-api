package com.sample.payconiq.stocks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import static com.sample.payconiq.stocks.utils.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class StockRequest {

    @NotBlank(message = NOT_BLANK_MESSAGE)
    private String stockName;
    @DecimalMin(message = DECIMAL_MIN_MESSAGE, value = DECIMAL_MIN_VALUE)
    @DecimalMax(value=DECIMAL_MAX_VALUE, message = DECIMAL_MAX_MESSAGE)
    private double currentPrice;


}
