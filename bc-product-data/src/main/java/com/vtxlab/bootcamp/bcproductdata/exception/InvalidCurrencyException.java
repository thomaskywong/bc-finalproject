package com.vtxlab.bootcamp.bcproductdata.exception;

import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;

public class InvalidCurrencyException extends IllegalArgumentException {

  public InvalidCurrencyException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
