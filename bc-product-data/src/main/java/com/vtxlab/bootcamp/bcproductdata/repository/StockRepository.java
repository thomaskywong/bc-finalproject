package com.vtxlab.bootcamp.bcproductdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vtxlab.bootcamp.bcproductdata.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
  
}
