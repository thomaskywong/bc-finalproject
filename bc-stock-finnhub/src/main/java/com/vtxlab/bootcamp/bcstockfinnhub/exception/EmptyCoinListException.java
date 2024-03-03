package com.vtxlab.bootcamp.bcstockfinnhub.exception;



import com.vtxlab.bootcamp.bcstockfinnhub.infra.Syscode;

public class EmptyCoinListException extends IllegalArgumentException {

  public EmptyCoinListException(Syscode syscode) {
    super(syscode.getMessage());
  }
} 
