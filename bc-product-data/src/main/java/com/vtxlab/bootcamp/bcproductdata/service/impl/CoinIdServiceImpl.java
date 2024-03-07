package com.vtxlab.bootcamp.bcproductdata.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcproductdata.dto.Coin;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinIdEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidCoinException;
import com.vtxlab.bootcamp.bcproductdata.infra.Scheme;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.mapper.CoinIdMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.UriCompBuilder;
import com.vtxlab.bootcamp.bcproductdata.model.ApiRespCoins;
import com.vtxlab.bootcamp.bcproductdata.model.CoinId;
import com.vtxlab.bootcamp.bcproductdata.repository.CoinIdRepository;
import com.vtxlab.bootcamp.bcproductdata.service.CoinIdService;

@Service
public class CoinIdServiceImpl implements CoinIdService {

  @Value(value = "${api.internal.crypto.host}")
  private String host;

  @Value(value = "${api.internal.crypto.port}")
  private int port;

  @Value(value = "${api.internal.crypto.basepath}")
  private String basepath;

  @Value(value = "${api.internal.crypto.endpoints.list}")
  private String listEndpoint;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CoinIdRepository coinIdRepository;

  // @Autowired
  // private CoinRepository coinRepostory;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CoinIdMapper coinIdMapper;

  @Override
  public List<CoinId> setCoinId(List<CoinId> ids)
      throws JsonProcessingException {

    List<Coin> coins = this.getCoins();

    List<CoinId> coinIdList = coins.stream() //
        .map(e -> {
          CoinId id = new CoinId();
          id.setCoinId(e.getId());
          return id;
        }) //
        .collect(Collectors.toList());

    if (!(coinIdList.containsAll(ids))) {
      throw new InvalidCoinException(Syscode.INVALID_COIN);
    }

    Set<CoinId> coinIds = coinIdRepository.findAll() //
        .stream() //
        .map(e -> coinIdMapper.mapCoinId(e)) //
        .collect(Collectors.toSet());

    for (CoinId id : ids) {
      if (coinIds.contains(id))
        continue;

      coinIdRepository.save(coinIdMapper.mapCoinIdEntity(id));
    }


    return ids;

  }

  @Override
  public List<CoinId> getCoinIds() throws JsonProcessingException {

    List<CoinIdEntity> updatedEntities = coinIdRepository.findAll();

    return updatedEntities.stream() //
        .map(e -> coinIdMapper.mapCoinId(e)) //
        .collect(Collectors.toList());

  }

  @Override
  public Boolean deleteCoinId(List<CoinId> ids) throws JsonProcessingException {

    Set<CoinId> coinIds = coinIdRepository.findAll() //
        .stream() //
        .map(e -> coinIdMapper.mapCoinId(e)) //
        .collect(Collectors.toSet());
    
    if (!(coinIds.containsAll(ids))) {
      return false;
    }

    List<String> idStrings = ids.stream().map(e -> e.getCoinId()).collect(Collectors.toList()); 

    List<CoinIdEntity> entitiesToRemove = coinIdRepository.findByCoinIdIn(idStrings);

    coinIdRepository.deleteAll(entitiesToRemove);
   
    return true;

  }

  @Override
  public Boolean deleteAllCoinIds() throws JsonProcessingException {

    coinIdRepository.deleteAll();

    return true;
  }

  @Override
  public List<Coin> getCoins() throws JsonProcessingException {

    String urlString2 =
        UriCompBuilder.url(Scheme.HTTP, host, port, basepath, listEndpoint);

    String JsonString = restTemplate.getForObject(urlString2, String.class);

    ApiRespCoins apiResp =
        objectMapper.readValue(JsonString, ApiRespCoins.class);

    List<Coin> coinList = apiResp.getData();

    return coinList;

  }

}
