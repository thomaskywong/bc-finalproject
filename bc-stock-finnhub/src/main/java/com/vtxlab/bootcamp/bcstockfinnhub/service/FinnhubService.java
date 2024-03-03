package com.vtxlab.bootcamp.bcstockfinnhub.service;



import java.util.List;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Profile2;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Quote;
import com.vtxlab.bootcamp.bcstockfinnhub.dto.Symbol;

@Service
public interface FinnhubService {

  Quote getQuote(String symbol);

  Profile2 getStockProfile2(String symbol);

  List<Symbol> getSymbols();

  void saveStockToRedis() throws JsonProcessingException;

}
