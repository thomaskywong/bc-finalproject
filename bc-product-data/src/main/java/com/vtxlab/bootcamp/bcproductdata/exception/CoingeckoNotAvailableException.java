package com.vtxlab.bootcamp.bcproductdata.exception;

import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;


public class CoingeckoNotAvailableException extends RestClientException {

  public CoingeckoNotAvailableException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
