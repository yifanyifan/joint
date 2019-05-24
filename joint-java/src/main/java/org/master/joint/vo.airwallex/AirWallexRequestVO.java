package org.master.joint.vo.airwallex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.master.joint.enums.IndustryCategoryEnum;
import org.master.joint.enums.PurposeEnum;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:01
 * @Description: 接口请求参数
 */
@Data
@ApiModel(value = "AirWallexRequestVO", description = "接口请求参数")
public class AirWallexRequestVO {
    @ApiModelProperty(name = "apiKey", value = "客户ID号", required = true)
    private String apiKey;

    @ApiModelProperty(name = "clientId", value = "API密钥", required = true)
    private String clientId;

    @ApiModelProperty(name = "agreedToTerms", value = "是否同意协议", required = true, example = "true")
    private Boolean agreedToTerms;

    @ApiModelProperty(name = "businessName", value = "公司名称", required = true)
    private String businessName;

    @ApiModelProperty(name = "countryCode", value = "国家二字编码（注册/经营）", required = true)
    private String countryCode;

    @ApiModelProperty(name = "state", value = "省（经营）", required = true)
    private String state;

    @ApiModelProperty(name = "suburb", value = "市（经营）", required = true)
    private String suburb;

    @ApiModelProperty(name = "addressLine1", value = "地址（经营）", required = true)
    private String addressLine1;

    @ApiModelProperty(name = "industryCategoryEnum", value = "子账号行业枚举", required = true, example = "电子商务市场平台")
    private IndustryCategoryEnum industryCategoryEnum;

    //@ApiModelProperty(name = "purpose", value = "用途", required = true, example = "电子商务收入的收取与支付")
    private String purpose;

    @ApiModelProperty(name = "purposeEnum", value = "用途枚举", required = true, example = "电子商务收入的收取与支付")
    private PurposeEnum purposeEnum;

    @ApiModelProperty(name = "url", value = "官网", required = true)
    private String url;

    @ApiModelProperty(name = "nationality", value = "法人国籍", required = true)
    private String nationality;

    @ApiModelProperty(name = "email", value = "子账号邮箱", required = true)
    private String email;

    /*@ApiModelProperty(name = "requestId", value = "唯一标识", required = true)
    private String requestId;*/
}
