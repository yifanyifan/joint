package org.master.joint.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/7
 * Modified By: 用途枚举
 */
public enum PurposeEnum {
    广告收入的收取与支付("Advertising income collection and payment", "0"),
    广告相关收入的收取与支付("Advertising associated income collection and payment", "1"),
    支付给自由职业者合作伙伴或供应商("Payment to freelance partners or suppliers", "2"),
    数字内容收入的收取与支付("Digital content income collection and payment", "3"),
    在线游戏收入和支付("Online game income and payment", "4"),
    向DC供应商付款("Payment to DC suppliers", "5"),
    电子商务收入的收取与支付("E-Commerce income collection and payment", "6"),
    支付给学校或老师("Payment to schools or teachers", "7"),
    物流供应商付款结算("Logistic supplier payment and settlement", "8"),
    商家结算("Merchant settlement", "9"),
    外汇转换("FX conversion", "10"),
    供应链融资与还款("Supply chain financing and repayment", "11"),
    P2P汇款("P2P remittance", "12"),
    OTA支付给自由合作伙伴或供应商("OTA payment to freelance partners or suppliers", "13"),
    货物贸易收入的收付("Goods trade income collection and payment", "14"),
    国际咨询费支付("International consulting fee payment", "15");

    PurposeEnum(String spec, String value) {
        this.spec = spec;
        this.value = value;
    }

    private String spec;
    private String value;

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

        for (PurposeEnum purposeEnum : PurposeEnum.values()) {
            map.put(purposeEnum.name(), purposeEnum.spec);
        }
        return map;
    }
}
