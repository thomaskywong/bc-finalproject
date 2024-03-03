package com.vxtlab.bootcamp.bccryptocoingecko.Service;



import java.util.List;

public interface ApiService<T> {

  List<T> fetchData();
  
}
