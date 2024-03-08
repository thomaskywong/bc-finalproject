package com.vtxlab.bootcamp.bcstockfinnhub.model;

import lombok.Getter;

@Getter
public enum StockSymbol {

  AAPL("AAPL"), 
  MSFT("MSFT"), 
  NVDA("NVDA"), 
  AMZN("AMZN"), 
  GOOGL("GOOGL"), 
  ;

  private String symbol;

  private StockSymbol(String symbol) {
    this.symbol = symbol;
  }
  
}
