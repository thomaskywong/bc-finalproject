package com.vtxlab.bootcamp.bcproductdata.mapper;

import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;

@Component
public class StockIdMapper {


  public StockId mapSymbolId(StockIdEntity entity) {
    return StockId.builder() //
        .stockId(entity.getStockId()) //
        .build();
  }

  public StockId mapSymbolId(String symbol) {
    return StockId.builder() //
        .stockId(symbol) //
        .build();
  }

  public StockIdEntity mapSymbolIdEntity(StockId id) {
    StockIdEntity stockIdEntity = new StockIdEntity();
    stockIdEntity.setStockId(id.getStockId());
    return stockIdEntity;
  }

  // public StockIdEntity mapSymbolIdEntity(StockId id, StockEntity stockEntity) {
  //   StockIdEntity stockIdEntity = new StockIdEntity();
  //   stockIdEntity.setStockId(id.getStockId());
  //   stockIdEntity.setStockEntity(stockEntity);
  //   return stockIdEntity;
  // }

  // public StockIdEntity mapSymbolIdEntity(StockId id,
  //     List<StockDailyEntity> stockDailyEntities) {
  //   StockIdEntity stockIdEntity = new StockIdEntity();
  //   stockIdEntity.setStockId(id.getStockId());
  //   stockIdEntity.setStockDailyEntities(stockDailyEntities);
  //   return stockIdEntity;
  // }

  // public StockIdEntity mapSymbolIdEntity(StockId id, StockEntity stockEntity,
  //     List<StockDailyEntity> stockDailyEntities) {
  //   StockIdEntity stockIdEntity = new StockIdEntity();
  //   stockIdEntity.setStockId(id.getStockId());
  //   stockIdEntity.setStockEntity(stockEntity);
  //   stockIdEntity.setStockDailyEntities(stockDailyEntities);
  //   return stockIdEntity;
  // }


}
