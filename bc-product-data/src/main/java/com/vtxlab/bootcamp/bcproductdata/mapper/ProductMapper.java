package com.vtxlab.bootcamp.bcproductdata.mapper;

import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.bcproductdata.dto.Product;
import com.vtxlab.bootcamp.bcproductdata.entity.CoinEntity;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;
import com.vtxlab.bootcamp.bcproductdata.model.ProductType;

@Component
public class ProductMapper {
  
  public Product mapProduct(CoinEntity entity) {

    return new Product(ProductType.CRYPTO.name(),//
                       entity.getCoinIdEntity().getCoinId(),//
                       entity.getName(),//
                       entity.getCurrPrice(),//
                       entity.getPriceChangePercent(),//
                       entity.getMarketCap(),//
                       entity.getLogo());
  }

  public Product mapProduct(StockEntity entity) {

    Double actualMarketCap = entity.getMarketCap() * 1_000_000;

    return new Product(ProductType.STOCK.name(),//
                       entity.getStockIdEntity().getStockId(),//
                       entity.getName(),//
                       entity.getCurrPrice(),//
                       entity.getPriceChangePercent(),//
                       actualMarketCap,//
                       entity.getLogo());
  }

}
