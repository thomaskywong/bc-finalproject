package com.vtxlab.bootcamp.bcproductdata.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vtxlab.bootcamp.bcproductdata.entity.StockIdEntity;

@Repository
public interface StockIdRepository extends JpaRepository<StockIdEntity, Long> {

    List<StockIdEntity> findByStockId(String stockId);

    List<StockIdEntity> findByStockIdIn(List<String> stockIds);

}
