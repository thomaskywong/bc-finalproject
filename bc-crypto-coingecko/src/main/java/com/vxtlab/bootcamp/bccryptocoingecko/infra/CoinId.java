package com.vxtlab.bootcamp.bccryptocoingecko.infra;



import lombok.Getter;

@Getter
public enum CoinId {

  BITCOIN ("bitcoin"),
  ETHEREUM ("ethereum"),
  TETHER ("tether"),
  ;

  private String id;

  private CoinId(String id) {
    this.id = id;
  }

  public static boolean isValidCoinId(String id) {
    
    for (CoinId coin : CoinId.values()) {
      if (coin.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  
}
