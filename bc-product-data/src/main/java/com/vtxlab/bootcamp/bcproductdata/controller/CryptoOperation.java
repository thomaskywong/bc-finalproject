package com.vtxlab.bootcamp.bcproductdata.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.dto.Product;
import com.vtxlab.bootcamp.bcproductdata.infra.ApiResponse;

public interface CryptoOperation {

  @GetMapping(value = "/product/coins")
  @ResponseStatus(value = HttpStatus.OK)
  @CrossOrigin
  ApiResponse<List<Product>> getCoinMarketPrices() throws JsonProcessingException;
  

}
