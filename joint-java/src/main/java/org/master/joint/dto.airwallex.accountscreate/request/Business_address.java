/**
  * Copyright 2019 bejson.com 
  */
package org.master.joint.dto.airwallex.accountscreate.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 17:54
 * @Description: 企业主要业务地址信息
 */
@Data
@ApiModel(value = "Business_address", description = "企业主要业务地址信息")
public class Business_address {
    @ApiModelProperty(name = "country_code", value = "国家编码")
    private String country_code;

    @ApiModelProperty(name = "address_line1", value = "地址")
    private String address_line1;

    @ApiModelProperty(name = "state", value = "州")
    private String state;

    @ApiModelProperty(name = "suburb", value = "市")
    private String suburb;
}