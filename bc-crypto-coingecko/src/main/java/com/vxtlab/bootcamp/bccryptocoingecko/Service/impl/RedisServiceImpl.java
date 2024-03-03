package com.vxtlab.bootcamp.bccryptocoingecko.Service.impl;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.vxtlab.bootcamp.bccryptocoingecko.Service.RedisService;
import com.vxtlab.bootcamp.bccryptocoingecko.exception.CoingeckoNotAvailableException;
import com.vxtlab.bootcamp.bccryptocoingecko.infra.Syscode;


@Service
public class RedisServiceImpl implements RedisService {
  
  private final RedisTemplate<String, String> redisTemplate;
  
  // @Autowired
  public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public String setValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    redisTemplate.expire(key, Duration.ofSeconds(60));
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public String getValue(String key) {
    String value = redisTemplate.opsForValue()  .get(key);
    if (value == null) {
      throw new CoingeckoNotAvailableException(Syscode.COINGECKO_NOT_AVAILABLE_EXCEPTION);
    }
    return value;
  }
  
  
}
