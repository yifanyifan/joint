package org.master.joint.dto.airwallex.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:00
 * @Description: 子账户划拨到主账户请求数据
 */
@Data
public class ChargesCreateRequest {
    /**
     * 发生额
     */
    private BigDecimal amount;
    /**
     * 货币
     */
    private String currency;
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
}
