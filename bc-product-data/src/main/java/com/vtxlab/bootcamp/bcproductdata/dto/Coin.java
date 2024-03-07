package com.vtxlab.bootcamp.bcproductdata.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

// @Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Coin {

  private String id;
  private String symbol;
  private String name;

  public String getId() {
    return this.id;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public String getName() {
    return this.name;
  }

  // public static boolean isValidCoin(List<Coin> coins, String coin) {
  //   System.out.println("validation");
  //   int size = coins.size();
  //   for (int i = 0; i < size; i++) {
  //     System.out.println(coins.get(i).getId() + ", coin=" + coin);
  //     if(coin.equals(coins.get(i).getId())) {
  //       System.out.println("true");
  //       return true;
  //     }
  //   }
  //   return false;
  // }
  
}
