package org.master.joint.enums;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/7
 * Modified By: 用途枚举
 */
public enum UrlEnum {
    电子商务市场平台("E-Commerce Marketplace - Platform"),
    电子商务市场批发商("E-Commerce Marketplace - Merchant"),
    数字内容和在线游戏("Digital Content & Online Gaming"),
    旅行("Travel");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private UrlEnum(String desc) {
        this.desc = desc;
    }
}
