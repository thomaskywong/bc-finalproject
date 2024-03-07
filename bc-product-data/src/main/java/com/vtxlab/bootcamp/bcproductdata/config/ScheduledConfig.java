package com.vtxlab.bootcamp.bcproductdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.service.CryptoService;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;

@Configuration
@EnableScheduling
public class ScheduledConfig {

  @Autowired
  private CryptoService cryptoService;

  @Autowired
  private FinnhubService finnhubService;

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "* * 0 * * *") // every xx:xx:00
  void reflashCryptoFromDB() throws JsonProcessingException {
    cryptoService.clearCoinsFromDB();
    cryptoService.storeCoinsToDB();
  }

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  void reflashQuotesToDB() throws JsonProcessingException {
    finnhubService.clearQuotesFromDB();
    finnhubService.saveQuotesToDB();
  }

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  void reflashProfilesToDB() throws JsonProcessingException {
    finnhubService.clearProfilesFromDB();
    finnhubService.saveProfilesToDB();
  }

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  void reflashCoinsPricesToDB() throws JsonProcessingException {
    cryptoService.clearCoinEntitiesFromDB();
    cryptoService.storeCoinEntitiesToDB();
  }



}
