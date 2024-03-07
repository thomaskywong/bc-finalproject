package com.vtxlab.bootcamp.bcproductdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  // @Bean
  // RedisTemplate<String, String> redisTemplate() {
  //   return new RedisTemplate<>();
  // }

  // @Bean
  // ModelMapper modelMapper() {
  //   return new ModelMapper();
  // }
  
  
}
