package org.master.joint.dto.airwallex.accountscreate.redis;

import lombok.Data;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/9
 * Modified By: 新增子账户后返回数据集合
 */
@Data
public class AccountRedis {
    /**
     * ID
     */
    private String id;

    /**
     * Email
     */
    private String email;
}
