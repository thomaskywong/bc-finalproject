package com.vtxlab.bootcamp.bcproductdata.controller.impl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.controller.ProductOperation;
import com.vtxlab.bootcamp.bcproductdata.dto.Product;
import com.vtxlab.bootcamp.bcproductdata.dto.StockDailyDTO;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockDailyEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.infra.ApiResponse;
import com.vtxlab.bootcamp.bcproductdata.mapper.ProductMapper;
import com.vtxlab.bootcamp.bcproductdata.service.CryptoService;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;
import com.vtxlab.bootcamp.bcproductdata.service.StockDailyService;

@RestController
@RequestMapping(value = "/data/api/v1")
public class ProductController implements ProductOperation {

  @Autowired
  private CryptoService cryptoService;

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private StockDailyService stockDailyService;

  @Override
  public ApiResponse<List<Product>> getProducts()
      throws JsonProcessingException {

    List<Product> products = new LinkedList<>();

    List<Product> coinsProduct = cryptoService.getCoinMarketPrices()//
        .stream()//
        .map(e -> productMapper.mapProduct(e))//
        .collect(Collectors.toList());

    List<Product> stocksProduct = finnhubService.getStockMarketPrices()//
        .stream()//
        .map(e -> productMapper.mapProduct(e))//
        .collect(Collectors.toList());

    products.addAll(coinsProduct);
    products.addAll(stocksProduct);

    return ApiResponse.<List<Product>>builder()//
        .ok()//
        .data(products)//
        .build();

  }


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


  @Override
  public ApiResponse<List<Product>> getCoinMarketPrices()
      throws JsonProcessingException {

    List<CoinEntity> coinEntities = cryptoService.getCoinMarketPrices();

    List<Product> products = coinEntities.stream()//
        .map(e -> productMapper.mapProduct(e))//
        .collect(Collectors.toList());

    return ApiResponse.<List<Product>>builder()//
        .ok()//
        .data(products)//
        .build();

  }

  @Override
  public ApiResponse<List<StockDailyDTO>> getStockDaily(String symbol)
      throws JsonProcessingException {

    List<StockDailyEntity> stockDailyEntities =
        stockDailyService.getStockDaily(symbol);

    List<StockDailyDTO> stockDailyDTOs = stockDailyEntities.stream()//
        .map(StockDailyDTO::mapToStockDailyDTO)//
        .sorted(Comparator.comparing(StockDailyDTO::getTradeDate).reversed())//
        .collect(Collectors.toList());

    return ApiResponse.<List<StockDailyDTO>>builder()//
        .ok()//
        .data(stockDailyDTOs)//
        .build();

  }

}
