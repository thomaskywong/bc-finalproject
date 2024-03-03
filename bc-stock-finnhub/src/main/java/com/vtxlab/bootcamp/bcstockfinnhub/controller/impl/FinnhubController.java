package com.vtxlab.bootcamp.bcstockfinnhub.controller.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.controller.FinnhubOperation;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Symbol;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.FinnhubNotAvailableException;
import com.vtxlab.bootcamp.bcstockfinnhub.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.ApiResponse;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;
import com.vtxlab.bootcamp.bcstockfinnhub.service.RedisService;


@RestController
@RequestMapping(value = "/stock/finnhub/api/v1")
public class FinnhubController implements FinnhubOperation {

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private ObjectMapper objectMapper;

  // @Autowired
  // private StockIdMapper stockIdMapper;

  @Override
  public ApiResponse<Quote> getQuote(String symbol)
      throws JsonProcessingException {

    String key =
        new StringBuilder("stock:finnhub:quote:").append(symbol).toString();

    // System.out.println("key = " + key);
     
    String redisQuote = redisService.getValue(key);

    // System.out.println(redisQuote);

    Quote quote;
    // Quote quote = objectMapper.readValue(redisQuote, Quote.class);


    if (redisQuote != null) {
      quote = objectMapper.readValue(redisQuote, Quote.class);
    } else {
      List<Symbol> symbols = this.getSymbols();

      if (!(Symbol.isValidSymbol(symbols, symbol))) {
        throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
      }

      quote = finnhubService.getQuote(symbol);    
    }

    return ApiResponse.<Quote>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(quote) //
        .build();
  }

  @Override
  public ApiResponse<Profile2> getStockProfile(String symbol)
      throws JsonProcessingException {

    if (!(Symbol.isValidSymbol(symbol))) {
      throw new FinnhubNotAvailableException(
          Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION);
    }

    String key =
        new StringBuilder("stock:finnhub:profile2:").append(symbol).toString();
    String redisProfile = redisService.getValue(key);
    // Profile2 profile = objectMapper.readValue(redisProfile, Profile2.class);

    Profile2 profile;

    if (redisProfile != null) {
      profile = objectMapper.readValue(redisProfile, Profile2.class);
    } else {
      
      List<Symbol> symbols = this.getSymbols();

      if (!(Symbol.isValidSymbol(symbols, symbol))) {
        throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
      }

      profile = finnhubService.getStockProfile2(symbol);

    }

    return ApiResponse.<Profile2>builder() //
        .code(Syscode.OK.getCode()) //
        .message(Syscode.OK.getMessage()) //
        .data(profile) //
        .build();

  }

  @Override
  public List<Symbol> getSymbols() throws JsonProcessingException {

    return finnhubService.getSymbols();

  }



}
