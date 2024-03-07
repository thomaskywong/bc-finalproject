package com.vtxlab.bootcamp.bcproductdata.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface FinnhubService {

    Boolean saveQuotesToDB() throws JsonProcessingException;

    Boolean clearQuotesFromDB() throws JsonProcessingException;

    Boolean storeAAPLQuoteToDB() throws JsonProcessingException;

    Boolean saveProfilesToDB() throws JsonProcessingException;

    Boolean clearProfilesFromDB() throws JsonProcessingException;

    Boolean storeAAPLProfileToDB() throws JsonProcessingException;

    Boolean storeStockEntitiesToDB() throws JsonProcessingException;

    Boolean clearStockEntitiesFromDB() throws JsonProcessingException;

}
