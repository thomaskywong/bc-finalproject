package com.vxtlab.bootcamp.bccryptocoingecko.infra;

import com.vxtlab.bootcamp.bccryptocoingecko.exception.InvalidCurrencyException;

public enum Currency {
  USD,
  // IDR,
  // TWD,
  // EUR,
  // KRW,
  // JPY,
  // RUB,
  // CNY,
  // AED,
  // ARS,
  // AUD,
  // BDT,
  // BHD,
  // BMD,
  // BRL,
  // CAD,
  // CHF,
  // CLP,
  // CZK,
  // DKK,
  // GBP,
  // GEL,
  // HKD,
  // HUF,
  // ILS,
  // INR,
  // KWD,
  // LKR,
  // MMK,
  // MXN,
  // MYR,
  // NGN,
  // NOK,
  // NZD,
  // PHP,
  // PKR,
  // PLN,
  // SAR,
  // SEK,
  // SGD,
  // THB,
  // TRY,
  // UAH,
  // VEF,
  // VND,
  // ZAR, 
  // XDR,
  // BTC,
  // ETH,
  // LTC,
  // BCH,
  // BNB,
  // EOS,
  // XRP,
  // XLM,
  // LINK,
  // DOT,
  // YFI,
  // BITS,
  // SATS,
  // XAG,
  // XAU,
  ;

  public static Currency toCurrency(String currency) {
    for (Currency cur : Currency.values()) {
      if (cur.name().toLowerCase().equals(currency))
        return cur;
    }
    throw new InvalidCurrencyException(Syscode.INVALID_CURRENCY);
  }

  public static boolean isValidCurrency(String currency) {
    for (Currency cur : Currency.values()) {
      if (cur.name().toLowerCase().equals(currency))
        return true;
    }
    return false;
  }
}
