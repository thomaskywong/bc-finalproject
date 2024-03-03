package com.vtxlab.bootcamp.bcstockfinnhub.infra;



import com.vtxlab.bootcamp.bcstockfinnhub.exception.InvalidCurrencyException;


public enum Currency {
  USD,
  ;

  public static Currency toCurrency(String currency) {
    for (Currency cur : Currency.values()) {
      if (cur.name().toLowerCase().equals(currency))
        return cur;
    }
    throw new InvalidCurrencyException(Syscode.INVALID_CURRENCY);
  }
}
