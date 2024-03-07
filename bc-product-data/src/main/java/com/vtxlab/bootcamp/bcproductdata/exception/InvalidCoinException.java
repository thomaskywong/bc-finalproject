package com.vtxlab.bootcamp.bcproductdata.exception;

import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;

public class InvalidCoinException extends IllegalArgumentException {

  public InvalidCoinException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
