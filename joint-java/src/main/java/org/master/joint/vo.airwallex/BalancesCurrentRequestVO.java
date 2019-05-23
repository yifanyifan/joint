package org.master.joint.vo.airwallex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:01
 * @Description: 银行余额接口请求参数
 */
@Data
@ApiModel(value = "BalancesCurrentRequestVO", description = "银行余额接口请求参数")
public class BalancesCurrentRequestVO {
    @ApiModelProperty(name = "apiKey", value = "客户ID号", required = true)
    private String apiKey;

    @ApiModelProperty(name = "clientId", value = "API密钥", required = true)
    private String clientId;

    @ApiModelProperty(name = "email", value = "子账号邮箱", required = true)
    private String email;
}
