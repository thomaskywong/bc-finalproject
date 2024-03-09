package com.vtxlab.bootcamp.bcproductdata.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.bcproductdata.entity.StockDailyEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockIdMapper;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;
import com.vtxlab.bootcamp.bcproductdata.service.StockDailyService;

@Service
public class StockDailyServiceImpl implements StockDailyService {

  @Autowired
  private StockIdMapper stockIdMapper;

  @Autowired
  private StockIdRepository stockIdRepository;

  @Override
  public List<StockDailyEntity> getStockDaily(String symbol) {

    StockId id = stockIdMapper.mapSymbolId(symbol);

    StockIdEntity stockIdEntity =
        stockIdRepository.findByStockId(id.getStockId());

    return stockIdEntity.getStockDailyEntities();
  }



}
