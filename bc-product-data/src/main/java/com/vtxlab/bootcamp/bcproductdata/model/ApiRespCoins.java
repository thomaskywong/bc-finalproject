package com.vtxlab.bootcamp.bcproductdata.model;

import java.util.List;
import com.vtxlab.bootcamp.bcproductdata.dto.Coin;
import lombok.Getter;


@Getter
public class ApiRespCoins {

  private String code;
  private String message;
  private List<Coin> data;

}
