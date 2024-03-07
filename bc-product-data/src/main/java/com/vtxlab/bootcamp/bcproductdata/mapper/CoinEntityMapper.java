package com.vtxlab.bootcamp.bcproductdata.mapper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.dto.Market;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinIdEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.MarketEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidCoinException;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.model.CoinId;
import com.vtxlab.bootcamp.bcproductdata.repository.CoinIdRepository;

@Component
public class CoinEntityMapper {

  // @Autowired 
  // private CoinIdMapper coinIdMapper;

  @Autowired
  private CoinIdRepository coinIdRepository; 

  public CoinEntity mapCoinEntity(Market market, CoinId id) {

    List<CoinIdEntity> coinIdEntities = coinIdRepository.findByCoinId(id.getCoinId());

    if (coinIdEntities.size() == 0) {
      throw new InvalidCoinException(Syscode.INVALID_COIN);
    } 

    CoinIdEntity coinIdEntity = coinIdEntities.get(0);

    CoinEntity coinEntity = new CoinEntity(null, //
                                          id.getCoinId(),//
                                          market.getCurrentPrice(),//
                                          market.getPriceChangePercent24h(),//
                                          (double)(long) market.getMarketCap(),//
                                          market.getImage(), //
                                          // null);
                                          coinIdEntity);
 
    // coinIdEntity.setCoinEntity(coinEntity);
    
    return coinEntity;

  }
  
  
  public CoinEntity mapCoinEntity(MarketEntity market, CoinId id) {

    List<CoinIdEntity> coinIdEntities = coinIdRepository.findByCoinId(id.getCoinId());

    if (coinIdEntities.size() == 0) {
      throw new InvalidCoinException(Syscode.INVALID_COIN);
    } 

    CoinIdEntity coinIdEntity = coinIdEntities.get(0);

    CoinEntity coinEntity = new CoinEntity(null, //
                                          market.getName(),//
                                          market.getCurrentPrice(),//
                                          market.getPriceChangePct24h(),//
                                          market.getMarketCap(),//
                                          market.getImage(), //
                                          coinIdEntity);
 
    // coinIdEntity.setCoinEntity(coinEntity);
    
    return coinEntity;

  }

}
