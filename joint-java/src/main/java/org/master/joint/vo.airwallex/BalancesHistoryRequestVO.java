package org.master.joint.vo.airwallex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:01
 * @Description: 银行流水接口请求参数
 */
@Data
@ApiModel(value = "BalancesHistoryRequestVO", description = "银行流水接口请求参数")
public class BalancesHistoryRequestVO {
    @ApiModelProperty(name = "apiKey", value = "客户ID号", required = true)
    private String apiKey;

    @ApiModelProperty(name = "clientId", value = "API密钥", required = true)
    private String clientId;

    @ApiModelProperty(name = "currency", value = "货币类型（不填则查所有）", required = false)
    private String currency;

    @ApiModelProperty(name = "email", value = "子账户邮编", required = true)
    private String email;
}
