package com.vxtlab.bootcamp.bccryptocoingecko.Service;

public interface RedisService {

  String setValue(String key, String value); 

  String getValue(String key); 
  
}
