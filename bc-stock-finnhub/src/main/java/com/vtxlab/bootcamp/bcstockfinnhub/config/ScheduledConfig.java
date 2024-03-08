package com.vtxlab.bootcamp.bcstockfinnhub.config;



import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtxlab.bootcamp.bcstockfinnhub.service.FinnhubService;
import com.vtxlab.bootcamp.bcstockfinnhub.service.RedisService;
import lombok.Getter;

@Getter
@Configuration
@EnableScheduling
public class ScheduledConfig {

  private LocalDateTime finnhubUpdatedTime = LocalDateTime.MIN;

  @Autowired
  private RedisService redisService;

  @Autowired
  private FinnhubService finnhubService;

  @Autowired
  private ObjectMapper objectMapper;


  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  public void fixedRateTask() {

    try {

      finnhubService.saveStockToRedis();

      this.finnhubUpdatedTime = LocalDateTime.now();
      System.out.println("Finnhub - Redis update time= " + this.finnhubUpdatedTime);

    } catch (RestClientException | JsonProcessingException  ex) {

    } 

  }

}
