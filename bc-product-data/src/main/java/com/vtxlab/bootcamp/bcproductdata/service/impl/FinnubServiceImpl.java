package com.vtxlab.bootcamp.bcproductdata.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.dto.Profile2;
import com.vtxlab.bootcamp.bcproductdata.dto.Quote;
import com.vtxlab.bootcamp.bcproductdata.entity.ProfileEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.FinnhubNotAvailableException;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcproductdata.infra.ApiResponse;
import com.vtxlab.bootcamp.bcproductdata.infra.Scheme;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.mapper.ProfileMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.QuoteMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockIdMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.UriCompBuilder;
import com.vtxlab.bootcamp.bcproductdata.model.ApiRespProfile;
import com.vtxlab.bootcamp.bcproductdata.model.ApiRespQuote;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.model.StockSymbol;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockProfileRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockQuoteRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockRepository;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;
import com.vtxlab.bootcamp.bcproductdata.service.StockIdService;
import jakarta.transaction.Transactional;

@Service
public class FinnubServiceImpl implements FinnhubService {
  @Value(value = "${api.internal.stock.host}")
  private String host;

  @Value(value = "${api.internal.stock.port}")
  private int port;

  @Value(value = "${api.internal.stock.basepath}")
  private String basepath;

  @Value(value = "${api.internal.stock.endpoints.quote}")
  private String quoteEndpoint;

  @Value(value = "${api.internal.stock.endpoints.profile2}")
  private String profileEndpoint;

  @Value(value = "${api.internal.stock.endpoints.symbols}")
  private String symbolEndpoint;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StockIdRepository stockIdRepository;

  @Autowired
  private StockQuoteRepository stockQuoteRepository;

  @Autowired
  private StockProfileRepository stockProfileRepository;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private StockIdMapper stockIdMapper;

  @Autowired
  private QuoteMapper quoteMapper;

  @Autowired
  private ProfileMapper profileMapper;

  @Autowired
  private StockMapper stockMapper;

  @Autowired
  private StockIdService stockIdService;



  @Override
  @Transactional
  public Boolean saveProfilesToDB() throws JsonProcessingException {

    // System.out.println("Check1");
    // Get updated profile
    List<StockIdEntity> idEntities = stockIdRepository.findAll();

    List<StockId> ids = idEntities.stream()//
        .map(e -> stockIdMapper.mapSymbolId(e))//
        .collect(Collectors.toList());

    List<ProfileEntity> profileEntities = new LinkedList<>();

    for (StockId id : ids) {

      Profile2 profile = this.getProfile(id);

      Quote quote = this.getQuote(id);

      long timestamp = quote.getT();
      Instant instant = Instant.ofEpochSecond(timestamp);
      LocalDateTime localDateTime =
          LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

      ProfileEntity profileEntity =
          profileMapper.mapProfileEntity(profile, localDateTime, id);

      profileEntities.add(profileEntity);

    }

    stockProfileRepository.saveAll(profileEntities);

    return true;
  }

  @Override
  @Transactional
  public Boolean storeAAPLProfileToDB() throws JsonProcessingException {

    // Get profile
    StockId id = StockId.builder()//
        .stockId(StockSymbol.AAPL.name())//
        .build();

    Profile2 profile = this.getProfile(id);

    Quote quote = this.getQuote(id);

    long timestamp = quote.getT();
    Instant instant = Instant.ofEpochSecond(timestamp);
    LocalDateTime localDateTime =
        LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

    // map to entity
    ProfileEntity profileEntity =
        profileMapper.mapProfileEntity(profile, localDateTime, id);

    System.out.println(profileEntity);

    stockProfileRepository.save(profileEntity);

    return true;

  }

  @Override
  public Boolean clearProfilesFromDB() {
    stockProfileRepository.deleteAll();
    return true;
  }


  @Override
  @Transactional
  public Boolean saveQuotesToDB() throws JsonProcessingException {

    List<StockIdEntity> idEntities = stockIdRepository.findAll();

    List<StockId> ids = idEntities.stream()//
        .map(e -> stockIdMapper.mapSymbolId(e))//
        .collect(Collectors.toList());

    List<QuoteEntity> quoteEntities = new LinkedList<>();

    for (StockId id : ids) {

      Quote quote = this.getQuote(id);

      QuoteEntity quoteEntity = quoteMapper.mapQuoteEntity(quote, id);

      quoteEntities.add(quoteEntity);
    }

    // System.out.println("List=" + quoteEntities);

    stockQuoteRepository.saveAll(quoteEntities);

    return true;

  }

  @Override
  public Boolean clearQuotesFromDB() {
    stockQuoteRepository.deleteAll();
    return true;
  }

  @Override
  public Boolean storeAAPLQuoteToDB() throws JsonProcessingException {

    StockId id = StockId.builder()//
        .stockId(StockSymbol.AAPL.name())//
        .build();


    Quote quote = this.getQuote(id);

    QuoteEntity quoteEntity = quoteMapper.mapQuoteEntity(quote, id);

    stockQuoteRepository.save(quoteEntity);

    return true;

  }

  @Override
  @Transactional
  public Boolean storeStockEntitiesToDB() throws JsonProcessingException {


    List<StockId> stockIds = stockIdService.getStockIds();

    System.out.println(stockIds);


    List<StockEntity> stockEntities = new ArrayList<>();


    for (StockId id : stockIds) {

      // List<QuoteEntity> qEntityList =
      //     stockQuoteRepository.findByQuoteStockCode(id.getStockId());

      // List<ProfileEntity> pEntityList =
      //     stockProfileRepository.findByQuoteStockCode(id.getStockId());
      
      ProfileEntity pEntity = stockProfileRepository.getMostRecentProfileEntityBySymbol(id.getStockId());
      QuoteEntity qEntity = stockQuoteRepository.getMostRecentQuoteEntityBySymbol(id.getStockId());

      Profile2 profile;
      Quote quote;
      StockEntity stockEntity;

      if (pEntity == null || qEntity == null) {

        quote = this.getQuote(id);
        profile = this.getProfile(id);
        stockEntity = stockMapper.mapStockEntity(profile, quote, id);

      } else {

        stockEntity = stockMapper.mapStockEntity(pEntity, qEntity, id);

      }

      // if (qEntityList.size() == 0 || pEntityList.size() == 0) {

      //   quote = this.getQuote(id);
      //   profile = this.getProfile(id);
      //   stockEntity = stockMapper.mapStockEntity(profile, quote, id);

      // } else {

      //   QuoteEntity qEntity = qEntityList.get(0);
      //   ProfileEntity pEntity = pEntityList.get(0);
      //   stockEntity = stockMapper.mapStockEntity(pEntity, qEntity, id);
      // }

      List<StockIdEntity> stockIdEntities =
          stockIdRepository.findByStockId(id.getStockId());


      StockIdEntity stockIdEntity = stockIdEntities.get(0);

      // System.out.println(stockIdEntity);

      stockIdEntity.setStockEntity(stockEntity);

      stockEntities.add(stockEntity);
    }

    // System.out.println(stockEntities);

    // stockRepository.deleteAll();
    stockRepository.saveAll(stockEntities);

    return true;
  }


  @Override
  public Boolean clearStockEntitiesFromDB() throws JsonProcessingException {

    List<StockId> stockIds = stockIdService.getStockIds();

    for (StockId id : stockIds) {

      List<StockIdEntity> stockIdEntities =
          stockIdRepository.findByStockId(id.getStockId());
      StockIdEntity stockIdEntity = stockIdEntities.get(0);

      stockIdEntity.setStockEntity(null);

    }


    stockRepository.deleteAll();

    return true;

  }

  @Override
  public List<StockEntity> getStockMarketPrices() {
    return stockRepository.findAll();
  }


  // @Override
  // public ProfileEntity getStockProfileEntitiesFromDB(String symbol)
  //     throws JsonProcessingException {

  //   List<ProfileEntity> profileEntity =
  //       stockProfileRepository.findByQuoteStockCode(symbol);

  //   if (profileEntity.size() == 0) {
  //     throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
  //   }

  //   return profileEntity.get(0);
  // }

  // @Override
  // public QuoteEntity getStockQuoteEntitiesFromDB(String symbol)
  //     throws JsonProcessingException {

  //   List<QuoteEntity> quoteEntity =
  //       stockQuoteRepository.findByQuoteStockCode(symbol);

  //   if (quoteEntity.size() == 0) {
  //     throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
  //   }

  //   return quoteEntity.get(0);
  // }

  private Profile2 getProfile(StockId id) {

    // System.out.println("stockId=" + id);

    String urlString = UriCompBuilder.urlProfile(Scheme.HTTP, host, port,
        basepath, profileEndpoint, id);

    // System.out.println(urlString);

    ApiRespProfile apiRespProfile =
        restTemplate.getForObject(urlString, ApiRespProfile.class);

    try {
      Objects.requireNonNull(apiRespProfile);
    } catch (NullPointerException ex) {
      throw new FinnhubNotAvailableException(
          Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION);
    }

    return apiRespProfile.getData();

  }


  private Quote getQuote(StockId id) {
    // get updated quote time
    String urlString2 = UriCompBuilder.urlQuote(Scheme.HTTP, host, port,
        basepath, quoteEndpoint, id);

    ApiRespQuote apiRespQuote =
        restTemplate.getForObject(urlString2, ApiRespQuote.class);

    try {
      Objects.requireNonNull(apiRespQuote);
    } catch (NullPointerException ex) {
      throw new FinnhubNotAvailableException(
          Syscode.FINNHUB_NOT_AVAILABLE_EXCEPTION);
    }

    return apiRespQuote.getData();

  }

}
