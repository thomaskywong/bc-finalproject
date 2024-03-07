package com.vtxlab.bootcamp.bcproductdata.mapper;

import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinIdEntity;
import com.vtxlab.bootcamp.bcproductdata.model.CoinId;

@Component
public class CoinIdMapper {

  public CoinId mapCoinId(CoinIdEntity entity) {
    return CoinId.builder() //
                 .coinId(entity.getCoinId()) //
                 .build();
  }

  public CoinIdEntity mapCoinIdEntity(CoinId id) {

    CoinIdEntity coinIdEntity = new CoinIdEntity(null, id.getCoinId(), null);

    return coinIdEntity;
  }
  
}
