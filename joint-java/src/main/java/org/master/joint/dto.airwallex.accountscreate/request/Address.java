/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 18:03
 * @Description: 企业注册地址信息
 */
@Data
@ApiModel(value = "Address", description = "企业注册地址信息")
public class Address {
    @ApiModelProperty(name = "country_code", value = "国家编码")
    private String country_code;
}