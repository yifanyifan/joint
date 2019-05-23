package org.master.joint.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/7
 * Modified By: 用途枚举
 */
public enum ReasonEnum {
    工资薪金("wages_salary", "0"),
    捐赠_慈善捐款("donation_charitable_contribution", "1"),
    个人汇款("personal_remittance", "2"),
    转账到自己的账户("transfer_to_own_account", "3"),
    退休金("pension", "4"),
    家族支持("family_support", "5"),
    生活费("living_expenses", "6"),
    教育培训("education_training", "7"),
    旅行("travel", "8"),
    投资收益("investment_proceeds", "9"),
    投资_资本("investment_capital", "10"),
    贷款_信贷_还款("loan_credit_repayment", "11"),
    税("taxes", "12"),
    购买商品("goods_purchased", "13"),
    业务支出("business_expenses", "14"),
    医疗服务("medical_services", "15"),
    专业的商业服务("professional_business_services", "16"),
    技术服务("technical_services", "17"),
    其他服务("other_services", "18"),
    建设("construction", "19"),
    货运("freight", "20"),
    房地产("real_estate", "21");

    /**
     * 英文说明
     */
    private String spec;
    /**
     * value值
     */
    private String value;

    ReasonEnum(String spec, String value) {
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

        for (ReasonEnum industryCategoryEnum : ReasonEnum.values()) {
            map.put(industryCategoryEnum.name(), industryCategoryEnum.spec);
        }
        return map;
    }
}
