package com.vtxlab.bootcamp.bcproductdata.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.dto.Quote;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;

@Component
public class QuoteMapper {

  public QuoteEntity mapQuoteEntity(Quote quote, StockId stockId) {

    long timestamp = quote.getT();
    Instant instant = Instant.ofEpochSecond(timestamp);
    LocalDateTime localDateTime =
        LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

    return QuoteEntity.builder() //
        .quoteDate(localDateTime) //
        .quoteStockCode(stockId.getStockId()) //
        .currPrice(quote.getC()) //
        .priceChg(quote.getD()) //
        .priceChgPct(quote.getDp())//
        .priceDayHigh(quote.getH()) //
        .priceDayLow(quote.getL()) //
        .pricePrevOpen(quote.getO())//
        .pricePrevClose(quote.getPc())//
        .build();

    // QuoteEntity entity = new QuoteEntity(null,//
    // localDateTime,//
    // stockId.getStockId(),//
    // quote.getC(),//
    // quote.getD(),//
    // quote.getDp(),//
    // quote.getH(),//
    // quote.getL(),//
    // quote.getO(),//
    // quote.getPc()
    // );

    // return entity;
  }

}


