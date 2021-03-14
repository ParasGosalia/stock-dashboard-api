package com.sample.payconiq.stocks.controller;

import com.sample.payconiq.stocks.filter.AuthenticationRequestFilter;
import com.sample.payconiq.stocks.model.StockResponse;
import com.sample.payconiq.stocks.services.StocksService;
import com.sample.payconiq.stocks.services.UserDetailsServiceImpl;
import com.sample.payconiq.stocks.utils.JwtTokenUtil;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers={StocksController.class,AuthenticationController.class})
@AutoConfigureMockMvc(addFilters =false)
public class StockControllerTest {

   @MockBean
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    StocksService stocksService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    AuthenticationRequestFilter authenticationRequestFilter;

    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    public void givenWhenStockDataProvided_addItToH2DB() throws Exception {
        String jsonStr = "{\n \"stockName\" : \"ANC\",\n \"currentPrice\" : 300.0\n} ";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8080/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    public void givenWhenRequestForAllStocksIsMade_ReturnAllTheStocks() throws Exception {

        StockResponse stockResponse = new StockResponse(1,"GOOGLE",300, "2020/02/20");
        StockResponse stockResponse1 = new StockResponse(1,"AMAZON",500, "2020/02/20");
        List<StockResponse> responseList = new ArrayList<>();
        responseList.add(stockResponse);
        responseList.add(stockResponse1);
        Mockito.when(
                stocksService.getAllStocks()
        ).thenReturn(responseList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8080/api/stocks");
        MvcResult result = mvc.perform(requestBuilder).andReturn();
       Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }

    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    public void givenSingleStockIsRequested_ReturnStock() throws Exception {

        StockResponse stockResponse = new StockResponse(1,"GOOGLE",300, "2020/02/20");

        Mockito.when(
                stocksService.getStockById(Mockito.anyLong())
        ).thenReturn(stockResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8080/api/stocks/1")
                 .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }


    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    public void givenSingleStockIsUpdated_ThenUpdateStockInH2DB() throws Exception {
        StockResponse stockResponse = new StockResponse(1,"GOOGLE",300, "2020/02/20");
        Mockito.when(
                stocksService.updateStock(Mockito.anyLong(), Mockito.anyDouble())
        ).thenReturn(stockResponse);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8080/api/stocks/1").
                param("currentPrice", String.valueOf(300))
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    }


    @WithMockUser(authorities = "ROLE_ADMIN")
    @Test
    public void givenSingleStockDeleteIsRequest_ThenDeleteStockFromH2DB() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("http://localhost:8080/api/stocks/1");
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

    }

}
