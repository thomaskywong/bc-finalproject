package com.vtxlab.bootcamp.bcstockfinnhub.config;



import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;



@Configuration
public class AppRunner implements CommandLineRunner{

  @Autowired
  private FinnhubService finnhubService;

  @Override
  public void run(String... args) throws Exception {
      finnhubService.saveStockToRedis();
      System.out.println("Finnhub - Redis Initialization Time=" + LocalDateTime.now());
  }
  
}
