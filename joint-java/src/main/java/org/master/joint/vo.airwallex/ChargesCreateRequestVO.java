package org.master.joint.vo.airwallex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.master.joint.enums.ReasonEnum;

import java.math.BigDecimal;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:01
 * @Description: 银行流水接口请求参数
 */
@Data
@ApiModel(value = "ChargesCreateRequestVO", description = "子账户划拨到主账户请求参数")
public class ChargesCreateRequestVO {
    @ApiModelProperty(name = "apiKey", value = "客户ID号", required = true)
    private String apiKey;

    @ApiModelProperty(name = "clientId", value = "API密钥", required = true)
    private String clientId;

    @ApiModelProperty(name = "amount", value = "金额", required = true, example = "1")
    private BigDecimal amount;

    @ApiModelProperty(name = "currency", value = "货币类型", required = true)
    private String currency;

    @ApiModelProperty(name = "reason", value = "用途", required = true, example = "旅行")
    private ReasonEnum reason;

    @ApiModelProperty(name = "reference", value = "说明", required = true)
    private String reference;

    @ApiModelProperty(name = "email", value = "子账户邮编", required = true)
    private String email;
}
