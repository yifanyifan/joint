/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 17:42
 * @Description: 详细描述
 */
@Data
@ApiModel(value = "Account_details", description = "详细描述")
public class Account_details {
    @ApiModelProperty(name = "business_details", value = "公司详情")
    private Business_details business_details;

    @ApiModelProperty(name = "legal_rep_details", value = "法人详情")
    private Legal_rep_details legal_rep_details;
}