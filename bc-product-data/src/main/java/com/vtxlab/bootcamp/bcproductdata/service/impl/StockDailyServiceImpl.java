package com.vtxlab.bootcamp.bcproductdata.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcproductdata.dto.StockDailyDTO;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockDailyEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockDailyMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockIdMapper;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockQuoteRepository;
import com.vtxlab.bootcamp.bcproductdata.service.RedisService;
import com.vtxlab.bootcamp.bcproductdata.service.StockDailyService;
import jakarta.transaction.Transactional;

@Service
public class StockDailyServiceImpl implements StockDailyService {

  @Autowired
  private StockIdMapper stockIdMapper;

  @Autowired
  private StockQuoteRepository stockQuoteRepository;

  @Autowired
  private StockIdRepository stockIdRepository;

  @Autowired
  private StockDailyMapper stockDailyMapper;

  @Autowired
  private RedisService redisService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public List<StockDailyEntity> getStockDaily(String symbol) {

    StockId id = stockIdMapper.mapSymbolId(symbol);

    QuoteEntity qEntity =
        stockQuoteRepository.getMostRecentQuoteEntityBySymbol(symbol);

    StockDailyEntity todayEntity =
        stockDailyMapper.mapStockDailyEntity(qEntity, id);

    System.out.println("Today Entity = " + todayEntity);


    StockIdEntity stockIdEntity =
        stockIdRepository.findByStockId(id.getStockId());

    List<StockDailyEntity> stockDailyEntities =
        stockIdEntity.getStockDailyEntities();

    // Decending order
    Collections.sort(stockDailyEntities,
        (e1, e2) -> e2.getTradeDate().compareTo(e1.getTradeDate()));

    if (!(stockDailyEntities.get(0).getTradeDate()
        .equals(todayEntity.getTradeDate()))) {
      stockDailyEntities.add(todayEntity);
    }

    return stockDailyEntities;
  }


  @Override
  @Transactional
  public void saveDataToRedis() throws JsonProcessingException {

    List<StockIdEntity> idEntities = stockIdRepository.findAll();

    List<StockId> ids = idEntities.stream()//
        .map(e -> stockIdMapper.mapSymbolId(e))//
        .collect(Collectors.toList());

    for (StockId id : ids) {

      String keyString = new StringBuilder("stock:daily:")//
          .append(id.getStockId())//
          .toString();

      // System.out.println("key = " + keyString);

      StockIdEntity stockIdEntity =
          stockIdRepository.findByStockId(id.getStockId());

      List<StockDailyEntity> stockDailyEntities =
          stockIdEntity.getStockDailyEntities();

      List<StockDailyDTO> stockDailyDTOs = stockDailyEntities.stream()//
          .map(StockDailyDTO::mapToStockDailyDTO)//
          .collect(Collectors.toList());

      String value = objectMapper.writeValueAsString(stockDailyDTOs);

      // System.out.println("value = " + value);


      redisService.setValue(keyString, value);

    }


  }



}
