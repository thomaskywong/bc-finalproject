package com.vxtlab.bootcamp.bccryptocoingecko.model;


import com.vxtlab.bootcamp.bccryptocoingecko.infra.Currency;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Scheme;
import com.vxtlab.bootcamp.bccryptocoingecko.mapper.UriCompBuilder;

public class CryptoGeckoUrlBuilder {

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointCoins, String key) {

    String urlString =
        UriCompBuilder.url(scheme, domain, basepath, endpointCoins, key);

    return urlString;

  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointMarkets, Currency cur, String key) {

    return UriCompBuilder.url(scheme, domain, basepath, endpointMarkets, cur,
        key);

  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpointMarkets, Currency cur, String key, String... ids) {

    return UriCompBuilder.url(scheme, domain, basepath, endpointMarkets, cur,
        key, ids);

  }

}
