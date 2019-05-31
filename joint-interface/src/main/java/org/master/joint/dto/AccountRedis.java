package org.master.joint.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/9
 * Modified By: 新增子账户后返回数据集合
 */
@Data
public class AccountRedis implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * Email
     */
    private String email;
}
