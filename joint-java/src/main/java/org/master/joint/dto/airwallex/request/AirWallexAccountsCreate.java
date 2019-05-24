package org.master.joint.dto.airwallex.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:00
 * @Description: 创建子账号请求数据
 */
@Data
@ApiModel(value = "AirWallexAccountsCreate", description = "创建子账号请求数据")
public class AirWallexAccountsCreate {
    @ApiModelProperty(name = "account_details", value = "详细描述")
    private Account_details account_details;

    @ApiModelProperty(name = "primary_contact", value = "子账户")
    private Primary_contact primary_contact;

    @ApiModelProperty(name = "request_id", value = "唯一标识")
    private String request_id;
}
