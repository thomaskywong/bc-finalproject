package com.vxtlab.bootcamp.bccryptocoingecko.exception;

import com.vxtlab.bootcamp.bccryptocoingecko.infra.Syscode;

public class InvalidCurrencyException extends IllegalArgumentException {

  public InvalidCurrencyException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
