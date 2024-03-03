package com.vxtlab.bootcamp.bccryptocoingecko.mapper;



import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import com.vxtlab.bootcamp.bccryptocoingecko.dto.jph.Market;


public class CryptoCoinMapper {

  public static List<Market> map(List<Market> markets) {
    return markets;
  }

  public static List<Market> map(List<Market> markets, String... ids) {
    
    List<Market> selected = new LinkedList<>();

    for (String id : ids) {
      Optional<Market> market = markets.stream() //
                                       .filter(e -> e.getId().equals(id)) //
                                       .findFirst();
      if (market.isPresent()) {
        selected.add(market.get());
      }
    }
    return selected;
  }
}
