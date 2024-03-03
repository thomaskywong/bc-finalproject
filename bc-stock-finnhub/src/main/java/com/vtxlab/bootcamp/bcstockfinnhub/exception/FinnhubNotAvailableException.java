package com.vtxlab.bootcamp.bcstockfinnhub.exception;



import org.springframework.web.client.RestClientException;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

public class FinnhubNotAvailableException extends RestClientException{

  public FinnhubNotAvailableException(Syscode syscode) {
    super(syscode.getMessage());
  }
  
}
