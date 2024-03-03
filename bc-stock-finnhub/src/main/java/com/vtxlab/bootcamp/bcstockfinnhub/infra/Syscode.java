package com.vtxlab.bootcamp.bcstockfinnhub.infra;



import lombok.Getter;

@Getter
public enum Syscode {

  OK("000000", "OK."), //
  JPH_NOT_AVAILABLE("100001","JsonPlaceHolder API not available."), //
  INVALID_CURRENCY("200001","Invalid Currency."), //
  INVALID_COIN("200002","Invalid Coin Id."), //
  INVALID_STOCK_SYMBOL("200003","Invalid Stock Symbol."), //
  EMPTY_COIN_LIST("200004","Empty Coin List in Coin Ids Repository."), //
  EMPTY_STOCK_SYMBOL_LIST("200005","Empty Stock Symbol List in Stock Symbol Ids Repository."), //
  FINNHUB_NOT_AVAILABLE_EXCEPTION("900000", "RestClientException - Finnhub Service is unavailable"), //
  REST_CLIENT_EXCEPTION("900001", "RestClient Exception."), //
  JSON_PROCESSING_EXCEPTION("900002", "Json Procesing Exception."), //
  NPE_EXCEPTION("910000","Runtime Exception - NPE"), //
  GENERAL_EXCEPTION("999999","Exception"), //
  ;

  private String code;
  private String message;

  private Syscode (String code, String message) {
    this.code = code;
    this.message = message;
  }
  
}
