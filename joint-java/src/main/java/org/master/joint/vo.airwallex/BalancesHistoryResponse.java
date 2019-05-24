package org.master.joint.vo.airwallex;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Yifan
 * @Date: 2019/5/9 14:41
 * @Description: 银行流水
 */
@Data
public class BalancesHistoryResponse {
    /**
     * 消息
     */
    private String message;

    /**
     * 有未显示
     */
    private Boolean has_more;

    /**
     * 流水集合
     */
    private List<Item> items;

    @Data
    class Item {
        /**
         * 发生额
         */
        private BigDecimal amount;
        /**
         * 银行余额
         */
        private BigDecimal balance;
        /**
         * 货币
         */
        private String currency;
        /**
         * 描述
         */
        private String description;
        /**
         * 发生时间
         */
        private String posted_at;
        /**
         * 操作ID
         */
        private String source;
        /**
         * 操作类型
         */
        private String source_type;
    }
}
