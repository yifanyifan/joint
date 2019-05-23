package org.master.joint.enums;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/7
 * Modified By: 文件类型
 */
public enum IdentityFilesTagEnum {
    BUSINESS_LICENSE("营业执照"),
    PERSONAL_ID_FRONT("身份证正面"),
    PERSONAL_ID_BACK("身份证反面");

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private IdentityFilesTagEnum(String desc) {
        this.desc = desc;
    }
}
