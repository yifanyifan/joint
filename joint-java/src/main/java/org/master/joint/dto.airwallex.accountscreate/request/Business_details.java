/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 17:48
 * @Description: 公司详情
 */
@Data
@ApiModel(value = "Business_details", description = "公司详情")
public class Business_details {
    @ApiModelProperty(name = "agreed_to_terms", value = "是否同意协议")
    private boolean agreed_to_terms;

    @ApiModelProperty(name = "business_name", value = "企业全称")
    private String business_name;

    @ApiModelProperty(name = "address", value = "企业注册地址信息")
    private Address address;

    @ApiModelProperty(name = "attachments", value = "企业营业执照")
    private Attachments1 attachments;

    @ApiModelProperty(name = "business_address", value = "企业主要业务地址信息")
    private Business_address business_address;

    @ApiModelProperty(name = "industry_category", value = "子账号行业类型")
    private String industry_category;

    @ApiModelProperty(name = "industry_category", value = "用途枚举")
    private String purpose;

    @ApiModelProperty(name = "industry_category", value = "官方网站，如果industry选择为UrlEnum中的一种则必填")
    private String url;

}