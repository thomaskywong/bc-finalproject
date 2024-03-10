package com.vtxlab.bootcamp.bcproductdata.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.dto.Product;
import com.vtxlab.bootcamp.bcproductdata.dto.StockDailyDTO;
import com.vtxlab.bootcamp.bcproductdata.infra.ApiResponse;

public interface ProductOperation {

  @GetMapping(value = "/products")
  @ResponseStatus(value = HttpStatus.OK)
  @CrossOrigin
  ApiResponse<List<Product>> getProducts() throws JsonProcessingException;

  @GetMapping(value = "/product/stocks")
  @ResponseStatus(value = HttpStatus.OK)
  @CrossOrigin
  ApiResponse<List<Product>> getStockMarketPrices()
      throws JsonProcessingException;
      
  @GetMapping(value = "/product/coins")
  @ResponseStatus(value = HttpStatus.OK)
  @CrossOrigin
  ApiResponse<List<Product>> getCoinMarketPrices() throws JsonProcessingException;
  
  @GetMapping(value = "/product/stocks/daily")
  @ResponseStatus(value = HttpStatus.OK)
  @CrossOrigin
  ApiResponse<List<StockDailyDTO>> getStockDaily(@RequestParam String symbol) throws JsonProcessingException;


}
