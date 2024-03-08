package com.vtxlab.bootcamp.bcproductdata.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vtxlab.bootcamp.bcproductdata.entity.ProfileEntity;


@Repository
public interface StockProfileRepository extends JpaRepository<ProfileEntity, Long> {
  
  Optional<ProfileEntity> findByQuoteStockCode(String quoteStockCode);
}
