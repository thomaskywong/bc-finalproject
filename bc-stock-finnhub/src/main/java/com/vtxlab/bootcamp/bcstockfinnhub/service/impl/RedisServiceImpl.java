package com.vtxlab.bootcamp.bcstockfinnhub.service.impl;



import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.vtxlab.bootcamp.bcstockfinnhub.service.RedisService;

@Service
public class RedisServiceImpl implements RedisService {
  
  private final RedisTemplate<String, String> redisTemplate;
  
  // @Autowired
  public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void setValue(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    redisTemplate.expire(key, Duration.ofSeconds(60));
  }

  @Override
  public String getValue(String key) {
    String value = redisTemplate.opsForValue().get(key);
    return value;
  }
  
  
}
