package org.master.joint.vo.airwallex;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:00
 * @Description: 子账户划拨到主账户返回数据
 */
@Data
public class ChargesCreateResponseVO {
    /**
     * 发生额
     */
    private BigDecimal amount;
    /**
     * 创建时间
     */
    private String created_at;
    /**
     * 货币
     */
    private String currency;
    /**
     * 费用
     */
    private BigDecimal fee;
    /**
     * ID
     */
    private String id;
    /**
     * 用途
     */
    private String reason;
    /**
     * 说明
     */
    private String reference;
    /**
     * 请求ID
     */
    private String request_id;
    /**
     * 子账户ID
     */
    private String source;
    /**
     * 状态
     */
    private String status;
    /**
     * 更新时间
     */
    private String updated_at;
    /**
     * 消息
     */
    private String message;
}
