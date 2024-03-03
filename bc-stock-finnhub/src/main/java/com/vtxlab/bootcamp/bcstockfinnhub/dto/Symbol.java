package com.vtxlab.bootcamp.bcstockfinnhub.dto;



import java.util.List;
import com.vtxlab.bootcamp.bcstockfinnhub.model.StockSymbol;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Symbol {

  private String currency;
  private String description;
  private String displaySymbol;
  private String figi;
  private String isin;
  private String mic;
  private String symbol;
  private String symbol2;
  private String type;

  public static boolean isValidSymbol(List<Symbol> symbols, String symbol) {

    for (Symbol sym: symbols) {
      // System.out.println("input=" + symbol + ", " + "target=" + sym);
      if (sym.getSymbol().equals(symbol)) {
        return true;
      }
    }
    // System.out.println("Invalid Symbol!");
    return false;
  }

  public static boolean isValidSymbol(String symbol) {

    for (StockSymbol sym: StockSymbol.values()) {
      if (sym.name().toLowerCase().equals(symbol.toLowerCase())) {
        return true;
      }
    }
    return false;
  }

}

