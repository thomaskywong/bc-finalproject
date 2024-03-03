package com.vxtlab.bootcamp.bccryptocoingecko.Service;



import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vxtlab.bootcamp.bccryptocoingecko.dto.jph.Coin;
import com.vxtlab.bootcamp.bccryptocoingecko.dto.jph.Market;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Currency;


public interface CryptoGeckoService {
  
  // List<Market> getMarkets(Currency currency) throws RestClientException;

  List<Market> getMarkets(Currency currency, String... ids) throws Exception;

  void getDataToRedis() throws JsonProcessingException;

  List<Coin> getCoins();

}
