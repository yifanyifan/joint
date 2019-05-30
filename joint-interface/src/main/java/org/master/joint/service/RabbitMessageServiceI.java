package org.master.joint.service;

import org.master.joint.rabbit.RabbitMessage;

/**
 * @Author: Yifan
 * @Date: 2019/5/30 16:33
 * @Description:
 */
public interface RabbitMessageServiceI {
    void sendMessage(String exchange, String routerkey, RabbitMessage message);
}
