package org.master.joint.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Yifan
 * @Date: 2019/5/30 14:18
 * @Description: 封装队列消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMessage implements Serializable {
    /**
     * 操作类型
     */
    private String action;

    /**
     * 操作对象id
     */
    private String id;

    /**
     * 操作对象
     */
    private Object obj;
}
