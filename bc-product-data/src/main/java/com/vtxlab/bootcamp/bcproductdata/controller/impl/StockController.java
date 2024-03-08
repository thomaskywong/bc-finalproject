package com.vtxlab.bootcamp.bcproductdata.controller.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.controller.StockOperation;
import com.vtxlab.bootcamp.bcproductdata.dto.Product;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.infra.ApiResponse;
import com.vtxlab.bootcamp.bcproductdata.mapper.ProductMapper;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;

@RestController
@RequestMapping(value = "/data/api/v1")
public class StockController implements StockOperation {

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private ProductMapper productMapper;

  @Override
  public ApiResponse<List<Product>> getStockMarketPrices()
      throws JsonProcessingException {

    List<StockEntity> stockEntities = finnhubService.getStockMarketPrices();

    List<Product> products = stockEntities.stream()//
        .map(e -> productMapper.mapProduct(e))//
        .collect(Collectors.toList());

    return ApiResponse.<List<Product>>builder()//
        .ok()//
        .data(products)//
        .build();
   
  }


}
