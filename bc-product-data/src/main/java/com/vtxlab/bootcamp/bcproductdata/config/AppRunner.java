package com.vtxlab.bootcamp.bcproductdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.service.CryptoService;
import com.vtxlab.bootcamp.bcproductdata.service.FinnhubService;

@Component
public class AppRunner implements CommandLineRunner{

  @Autowired
  private CryptoService cryptoService;

  @Autowired
  private FinnhubService finnhubService;

  @Override
  public void run(String... args) throws Exception {

    cryptoService.clearCoinsFromDB();
    cryptoService.storeBitcoinsToDB();

    finnhubService.clearQuotesFromDB();
    finnhubService.storeAAPLQuoteToDB();

    finnhubService.clearProfilesFromDB();
    finnhubService.storeAAPLProfileToDB();

  }
  
}
