package com.vtxlab.bootcamp.bcproductdata.mapper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.dto.Profile2;
import com.vtxlab.bootcamp.bcproductdata.dto.Quote;
import com.vtxlab.bootcamp.bcproductdata.entity.ProfileEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;

@Component
public class StockMapper {

  @Autowired
  private StockIdRepository stockIdRepository;

  public StockEntity mapStockEntity(Profile2 profile, Quote quote, StockId id) {

    List<StockIdEntity> stockIdEntities =
        stockIdRepository.findByStockId(id.getStockId());

    if (stockIdEntities.size() == 0) {
      throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    StockIdEntity stockIdEntity = stockIdEntities.get(0);

    StockEntity stockEntity = new StockEntity(null, //
        id.getStockId(), //
        quote.getC(), //
        quote.getDp(), //
        profile.getMarketCapitalization(), //
        profile.getLogo(), //
        stockIdEntity); 

    return stockEntity;

  }

  public StockEntity mapStockEntity(ProfileEntity pEntity, QuoteEntity qEntity, StockId id) {

    List<StockIdEntity> stockIdEntities =
        stockIdRepository.findByStockId(id.getStockId());

    if (stockIdEntities.size() == 0) {
      throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
    }

    StockIdEntity stockIdEntity = stockIdEntities.get(0);

    StockEntity stockEntity = new StockEntity(null, //
                                  qEntity.getQuoteStockCode(),//
                                  qEntity.getCurrPrice(),//
                                  qEntity.getPriceChgPct(),//
                                  pEntity.getMarketCapitalization(),//
                                  pEntity.getLogo(),//
                                  stockIdEntity);

    return stockEntity;

  }
}
