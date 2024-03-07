package com.vtxlab.bootcamp.bcproductdata.model;

import com.vtxlab.bootcamp.bcproductdata.dto.Profile2;
import lombok.Getter;


@Getter
public class ApiRespProfile {

  private String code;
  private String message;
  private Profile2 data;

}
