/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 17:45
 * @Description: 子账户
 */
@Data
public class Primary_contact {
    @ApiModelProperty(name = "email", value = "子账户邮箱")
    private String email;
}