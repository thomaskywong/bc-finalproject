package com.vtxlab.bootcamp.bcproductdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.service.CryptoService;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;
import jakarta.transaction.Transactional;

@Configuration
@EnableScheduling
public class ScheduledConfig {

  @Autowired
  private CryptoService cryptoService;

  @Autowired
  private FinnhubService finnhubService;

  @Scheduled(fixedRate = 60000)
  // @Scheduled(cron = "* * 0 * * *") // every xx:xx:00
  @Transactional
  void reflashCryptoDB() throws JsonProcessingException {
    // cryptoService.clearCoinsFromDB();
    cryptoService.storeCoinsToDB();

  }

  @Scheduled(fixedRate = 60000)
  // @Scheduled(cron = "* * 0 * * *") // every xx:xx:00
  @Transactional
  void reflashCoinEntityDB() throws JsonProcessingException {
    cryptoService.clearCoinEntitiesFromDB();
    cryptoService.storeCoinEntitiesToDB();
  }

  @Scheduled(fixedRate = 60000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  @Transactional
  void reflashStocksDB() throws JsonProcessingException {
    // finnhubService.clearProfilesFromDB();
    finnhubService.saveProfilesToDB();
    // finnhubService.clearQuotesFromDB();
    finnhubService.saveQuotesToDB();
  }

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  @Transactional
  void reflashStockEntityDB() throws JsonProcessingException {
    finnhubService.clearStockEntitiesFromDB();
    finnhubService.storeStockEntitiesToDB();
  }




}
