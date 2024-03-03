package com.vxtlab.bootcamp.bccryptocoingecko.dto.jph;



import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Coin {

  private String id;
  private String symbol;
  private String name;

  public static boolean isValidCoin(List<Coin> coins, String coin) {
    for (Coin c : coins) {
      if(c.getId().equals(coin))
        return true;
    }
    return false;
  }
  
}
