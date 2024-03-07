package com.vtxlab.bootcamp.bcproductdata.model;

import java.util.List;
import com.vtxlab.bootcamp.bcproductdata.dto.Market;
import lombok.Getter;


@Getter
public class ApiRespMarkets {

  private String code;
  private String message;
  private List<Market> data;

}
