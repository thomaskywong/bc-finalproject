package com.vxtlab.bootcamp.bccryptocoingecko.config;



import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vxtlab.bootcamp.bccryptocoingecko.Service.CryptoGeckoService;
import com.vxtlab.bootcamp.bccryptocoingecko.Service.RedisService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@EnableScheduling
public class ScheduledConfig {

  private LocalDateTime coingeckoUpdateTime = LocalDateTime.MIN;

  @Autowired
  private CryptoGeckoService cryptoGeckoService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private RedisService redisService;

  @Scheduled(fixedRate = 30000)
  // @Scheduled(cron = "0 * * * * *") // every xx:xx:00
  public void fixedRateTask() {

    try {
      cryptoGeckoService.getDataToRedis();
      this.coingeckoUpdateTime = LocalDateTime.now();
      System.out.println("Coingecko - Redis update time= " + this.coingeckoUpdateTime);

    } catch (RestClientException | JsonProcessingException ex) {

    }
  }
}
