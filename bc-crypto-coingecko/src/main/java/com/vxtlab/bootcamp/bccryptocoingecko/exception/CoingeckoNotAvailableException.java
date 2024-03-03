package com.vxtlab.bootcamp.bccryptocoingecko.exception;

import org.springframework.web.client.RestClientException;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Syscode;


public class CoingeckoNotAvailableException extends RestClientException {

  public CoingeckoNotAvailableException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
