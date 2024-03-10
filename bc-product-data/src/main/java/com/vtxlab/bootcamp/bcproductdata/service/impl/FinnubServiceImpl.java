package com.vtxlab.bootcamp.bcproductdata.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.dto.Profile2;
import com.vtxlab.bootcamp.bcproductdata.dto.Quote;
import com.vtxlab.bootcamp.bcproductdata.entity.ProfileEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.QuoteEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockDailyEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.exception.FinnhubNotAvailableException;
import com.vtxlab.bootcamp.bcproductdata.exception.InvalidStockSymbolException;
import com.vtxlab.bootcamp.bcproductdata.infra.Scheme;
import com.vtxlab.bootcamp.bcproductdata.infra.Syscode;
import com.vtxlab.bootcamp.bcproductdata.mapper.ProfileMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.QuoteMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockDailyMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockIdMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.StockMapper;
import com.vtxlab.bootcamp.bcproductdata.mapper.UriCompBuilder;
import com.vtxlab.bootcamp.bcproductdata.model.ApiRespProfile;
import com.vtxlab.bootcamp.bcproductdata.model.ApiRespQuote;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;
import com.vtxlab.bootcamp.bcproductdata.model.StockSymbol;
import com.vtxlab.bootcamp.bcproductdata.repository.StockDailyRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockIdRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockProfileRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockQuoteRepository;
import com.vtxlab.bootcamp.bcproductdata.repository.StockRepository;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;
import com.vtxlab.bootcamp.bcproductdata.service.StockIdService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
  private StockIdService stockIdService;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private StockIdRepository stockIdRepository;

  @Autowired
  private StockQuoteRepository stockQuoteRepository;

  @Autowired
  private StockProfileRepository stockProfileRepository;

  @Autowired
  private StockDailyRepository stockDailyRepository;

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
  private StockDailyMapper stockDailyMapper;

  @PersistenceContext
  private EntityManager entityManager;


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
  @Transactional
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

    List<StockEntity> stockEntities = new ArrayList<>();

    for (StockId id : stockIds) {

      ProfileEntity pEntity = stockProfileRepository
          .getMostRecentProfileEntityBySymbol(id.getStockId());
      QuoteEntity qEntity = stockQuoteRepository
          .getMostRecentQuoteEntityBySymbol(id.getStockId());

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

      StockIdEntity stockIdEntity =
          stockIdRepository.findByStockId(id.getStockId());

      if (stockIdEntity == null) {
        throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
      }

      stockIdEntity.setStockEntity(stockEntity);

      stockEntities.add(stockEntity);
    }

    stockRepository.saveAll(stockEntities);

    return true;
  }


  @Override
  @Transactional
  public Boolean clearStockEntitiesFromDB() throws JsonProcessingException {

    List<StockId> stockIds = stockIdService.getStockIds();

    for (StockId id : stockIds) {

      StockIdEntity stockIdEntity =
          stockIdRepository.findByStockId(id.getStockId());

      if (stockIdEntity == null) {
        throw new InvalidStockSymbolException(Syscode.INVALID_STOCK_SYMBOL);
      }

      stockIdEntity.setStockEntity(null);

    }


    stockRepository.deleteAll();

    return true;

  }

  @Override
  public List<StockEntity> getStockMarketPrices() {
    return stockRepository.findAll();
  }


  @Override
  @Transactional
  public Boolean reflashStockDailyEntityInDB() throws JsonProcessingException {

    this.saveQuotesToDB();

    List<StockId> ids = stockIdService.getStockIds();

    for (StockId id : ids) {

      // get most recent quote entity
      QuoteEntity recentQuoteEntity = stockQuoteRepository
          .getMostRecentQuoteEntityBySymbol(id.getStockId());

      // get StockIdEntity Object from repository
      StockIdEntity stockIdEntity =
          stockIdRepository.findByStockId(id.getStockId());

      // get recent quote date
      LocalDate recentDate = recentQuoteEntity.getQuoteDate().toLocalDate();

      // get
      StockDailyEntity oldEntity = stockDailyRepository
          .getDailyEntityByDateAndSymbol(stockIdEntity, recentDate);
      

      if (oldEntity != null) {

        Long primaryKey = oldEntity.getId();

        StockDailyEntity entity =
            entityManager.find(StockDailyEntity.class, primaryKey);

        entity.setDayHigh(recentQuoteEntity.getPriceDayHigh());
        entity.setDayLow(recentQuoteEntity.getPriceDayLow());
        entity.setDayOpen(recentQuoteEntity.getPricePrevOpen());
        entity.setDayClose(recentQuoteEntity.getCurrPrice());

        entityManager.merge(entity);

      } else {

        // build StockDailyEntity
        StockDailyEntity sDailyEntity =
            stockDailyMapper.mapStockDailyEntity(recentQuoteEntity, id);

        // Use EntityManager to find StockIdEntity from DB
        Long primaryKey = stockIdEntity.getId();

        StockIdEntity sIdEntity =
            entityManager.find(StockIdEntity.class, primaryKey);

        List<StockDailyEntity> stockDailyEntities =
            sIdEntity.getStockDailyEntities();

        // add StockDailyEntity to List
        stockDailyEntities.add(sDailyEntity);

        // Set new list into StockIdEntity
        sIdEntity.setStockDailyEntities(stockDailyEntities);

        // Use EntityManager to merge into DB
        entityManager.merge(sIdEntity);
      }

    }
    return true;
  }


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
