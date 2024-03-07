package com.vxtlab.bootcamp.bccryptocoingecko.infra;



import lombok.Getter;

@Getter
public enum CoinId {

  BITCOIN("bitcoin"), //
  ETHEREUM("ethereum"), //
  TETHER("tether"), //
  BNB("binancecoin"), //
  SOLANA("solana"),//
  LIDO_STAKED_ETHER("staked-ether"),//
  XRP("warioxrpdumbledoreyugioh69inu"),//
  USDC("zksync-bridged-usdc-zksync"),//
  CARDANO("ada-the-dog"),//
  DOGECOIN("binance-peg-dogecoin"),//
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
