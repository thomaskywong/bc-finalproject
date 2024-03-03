package com.vtxlab.bootcamp.bcstockfinnhub.mapper;



import org.springframework.web.util.UriComponentsBuilder;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.Scheme;
import com.vtxlab.bootcamp.bcstockfinnhub.infra.StockMarket;

public class UriCompBuilder {

  public static String url(Scheme scheme, String domain, String basepath,
      String endpoint, String symbol, String key) {
    return UriComponentsBuilder.newInstance() //
        .scheme(scheme.name().toLowerCase()) //
        .host(domain) //
        .path(basepath) //
        .path(endpoint) //
        .queryParam("symbol", symbol) //
        .queryParam("token", key) //
        .toUriString();
  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpoint, StockMarket market, String key) {
    return UriComponentsBuilder.newInstance() //
        .scheme(scheme.name().toLowerCase()) //
        .host(domain) //
        .path(basepath) //
        .path(endpoint) //
        .queryParam("exchange", market.name()) //
        .queryParam("token", key) //
        .toUriString();
  }

  public static String url(Scheme scheme, String domain, String basepath,
      String endpoint, String key) {
    return UriComponentsBuilder.newInstance() //
        .scheme(scheme.name().toLowerCase()) //
        .host(domain) //
        .path(basepath) //
        .path(endpoint) //
        .queryParam("exchange", "US") //
        .queryParam("token", key) //
        .toUriString();
  }

}
