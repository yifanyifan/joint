package org.master.joint.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/7
 * Modified By: 子账号行业类型
 */
public enum IndustryCategoryEnum {
    电子商务市场平台("E-Commerce Marketplace - Platform", "6"),
    电子商务市场批发商("E-Commerce Marketplace - Merchant", "6"),
    货物贸易("Goods trade", "14"),
    数字内容和在线游戏("Digital Content & Online Gaming", "2/3/4/5"),
    旅行("Travel", "13"),
    广告联属营销("Advertising / Affiliate Marketing", "0/1/2"),
    教育("Education", "7"),
    物流("Logistics", "8"),
    服务非财务相关("Services - Non Financial Related", "15"),
    支付服务提供商("Payment Service Provider", "9/10/11/12"),
    其他金融相关服务("Other Financial Related Services", "11/12"),
    其他("Other", "");

    /**
     * 英文说明
     */
    private String spec;
    /**
     * 对应PurposeEnum类value值
     */
    private String value;

    IndustryCategoryEnum(String spec, String value) {
        this.spec = spec;
        this.value = value;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();

        for (IndustryCategoryEnum industryCategoryEnum : IndustryCategoryEnum.values()) {
            map.put(industryCategoryEnum.name(), industryCategoryEnum.spec);
        }
        return map;
    }
}
