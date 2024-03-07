package com.vtxlab.bootcamp.bcproductdata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockDBOperation {

  @GetMapping(value = "/quote/save")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean saveQuoteToDB() throws JsonProcessingException;

  @GetMapping(value = "/quote/clear")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean clearQuotesFromDB() throws JsonProcessingException;

  @GetMapping(value = "/profile/save")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean saveProfileToDB() throws JsonProcessingException;

  @GetMapping(value = "/profile/clear")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean clearProfileToDB() throws JsonProcessingException;

  @GetMapping(value = "/quote/save_AAPL")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean saveAAPLQuoteToDB() throws JsonProcessingException;

  @GetMapping(value = "/profile/save_AAPL")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean saveAAPLProfileToDB() throws JsonProcessingException;

  @GetMapping(value = "/stock/entites/save")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean storeStockEntitiesToDB() throws JsonProcessingException;

  @GetMapping(value = "/stock/entites/clear")
  @ResponseStatus(value = HttpStatus.OK)
  Boolean clearStockEntitiesFromDB() throws JsonProcessingException;

  
}
