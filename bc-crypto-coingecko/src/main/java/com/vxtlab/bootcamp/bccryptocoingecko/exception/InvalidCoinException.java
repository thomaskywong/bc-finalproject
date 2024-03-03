package com.vxtlab.bootcamp.bccryptocoingecko.exception;

import com.vxtlab.bootcamp.bccryptocoingecko.infra.Syscode;

public class InvalidCoinException extends IllegalArgumentException {

  public InvalidCoinException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
