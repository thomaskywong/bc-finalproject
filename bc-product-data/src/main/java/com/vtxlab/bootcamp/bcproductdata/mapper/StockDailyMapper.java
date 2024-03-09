package com.vtxlab.bootcamp.bcproductdata.mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.dto.Quote;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockDailyEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;

@Component
public class StockDailyMapper {

  @Autowired
  private StockIdRepository stockIdRepository;


  public StockDailyEntity mapStockDailyEntity(Quote quote, StockId id) {

    List<StockIdEntity> stockIdEntities =
        stockIdRepository.findByStockId(id.getStockId());

    if (stockIdEntities.size() == 0) {
      throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    StockIdEntity stockIdEntity = stockIdEntities.get(0);

    long timestamp = quote.getT();
    Instant instant = Instant.ofEpochSecond(timestamp);
    LocalDate tradeDate = LocalDate.ofInstant(instant, ZoneOffset.UTC);

    StockDailyEntity stockDailyEntity = new StockDailyEntity(null, //
        tradeDate, //
        quote.getH(), //
        quote.getL(), //
        quote.getO(), //
        quote.getC(), //
        stockIdEntity);

    return stockDailyEntity;

  }


  public StockDailyEntity mapStockDailyEntity(QuoteEntity quoteEntity, StockId id) {

    List<StockIdEntity> stockIdEntities =
        stockIdRepository.findByStockId(id.getStockId());

    if (stockIdEntities.size() == 0) {
      throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    StockIdEntity stockIdEntity = stockIdEntities.get(0);

    StockDailyEntity stockDailyEntity = new StockDailyEntity(null, //
        quoteEntity.getQuoteDate().toLocalDate(), //
        quoteEntity.getPriceDayHigh(), //
        quoteEntity.getPriceDayLow(), //
        quoteEntity.getPricePrevOpen(), //
        quoteEntity.getCurrPrice(), //
        stockIdEntity);

    return stockDailyEntity;

  }
  
  

}
