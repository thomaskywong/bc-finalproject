package com.vtxlab.bootcamp.bcproductdata.exception;

import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;

public class InvalidStockSymbolException extends IllegalArgumentException {

  public InvalidStockSymbolException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
