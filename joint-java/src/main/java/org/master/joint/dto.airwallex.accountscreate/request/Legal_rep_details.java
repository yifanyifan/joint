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
 * @Description: 法人详情
 */
@Data
@ApiModel(value = "Legal_rep_details", description = "法人详情")
public class Legal_rep_details {
    @ApiModelProperty(name = "attachments", value = "法定代表人身份证正、反面")
    private Attachments2 attachments;

    @ApiModelProperty(name = "nationality", value = "国籍")
    private String nationality;
}