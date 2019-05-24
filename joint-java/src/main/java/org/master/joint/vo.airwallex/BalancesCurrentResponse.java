package org.master.joint.vo.airwallex;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Yifan
 * @Date: 2019/5/9 14:41
 * @Description: 银行余额
 */
@Data
public class BalancesCurrentResponse {
    /**
     * 可以金额
     */
    private BigDecimal available_amount;
    /**
     * 货币
     */
    private String currency;
    /**
     * 冻结金额
     */
    private BigDecimal pending_amount;
    /**
     * 总金额
     */
    private BigDecimal total_amount;
}
