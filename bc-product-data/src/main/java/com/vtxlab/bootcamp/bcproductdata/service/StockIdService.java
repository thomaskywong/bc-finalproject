package com.vtxlab.bootcamp.bcproductdata.service;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vtxlab.bootcamp.bcproductdata.dto.Symbol;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;
import com.vtxlab.bootcamp.bcproductdata.model.StockId;

public interface StockIdService {
    List<StockId> setStockId(List<StockId> ids) throws JsonProcessingException;

    List<StockId> getStockIds() throws JsonProcessingException;

    Boolean deleteStockId(List<StockId> ids) throws JsonProcessingException;

    Boolean deleteAllStockIds() throws JsonProcessingException;

    List<Symbol> getSymbols() throws JsonProcessingException;

    List<StockIdEntity> getStockIdEntities() throws JsonProcessingException;

}
