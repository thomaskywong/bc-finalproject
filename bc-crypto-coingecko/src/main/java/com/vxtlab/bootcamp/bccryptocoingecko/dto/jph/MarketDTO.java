package com.vxtlab.bootcamp.bccryptocoingecko.dto.jph;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketDTO {

  private String id;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "symbol")
  private String symbol;


  @JsonProperty(value = "market_cap")
  private int marketCap;

  @JsonProperty(value = "market_cap_rank")
  private int marketCapRank;

  @JsonProperty(value = "image")
  private String image;

  @JsonProperty(value = "current_price")
  private double currentPrice;

  @JsonProperty(value = "price_change_percentage_24h")
  private double priceChangePercent24h;

  @JsonProperty(value = "total_volume")
  private long totalVolume;


  public static MarketDTO mapMarketDTO(Market market) {

    return MarketDTO.builder() //
        .id(market.getId())//
        .name(market.getName()) //
        .symbol(market.getSymbol()) //
        .marketCapRank(market.getMarketCapRank()) //
        .marketCap(market.getMarketCapRank())//
        .image(market.getImage()) //
        .currentPrice(market.getCurrentPrice()) //
        .priceChangePercent24h(market.getPriceChangePercent24h()) //
        .totalVolume(market.getTotalVolume()) //
        .build();

  }

}
