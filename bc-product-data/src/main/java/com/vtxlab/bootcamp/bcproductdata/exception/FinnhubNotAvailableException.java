package com.vtxlab.bootcamp.bcproductdata.exception;

import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;

public class FinnhubNotAvailableException extends RestClientException{

  public FinnhubNotAvailableException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
