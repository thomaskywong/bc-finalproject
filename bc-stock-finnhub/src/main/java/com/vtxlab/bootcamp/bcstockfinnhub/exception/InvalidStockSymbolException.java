package com.vtxlab.bootcamp.bcstockfinnhub.exception;



import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

public class InvalidStockSymbolException extends IllegalArgumentException {

  public InvalidStockSymbolException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
