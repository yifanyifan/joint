package org.master.common.servicedubbo.rabbit;

import com.alibaba.dubbo.config.annotation.Service;
import org.master.common.service.rabbit.MessageProducer;
import org.master.joint.rabbit.RabbitMessage;
import org.master.joint.service.RabbitMessageServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @Author: Yifan
 * @Date: 2019/5/30 16:39
 * @Description: 发送Rabbit消息 Dubbo层
 */
@Service
public class RabbitMessageService implements RabbitMessageServiceI {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMessageService.class);

    @Resource
    private MessageProducer messageProducer;

    /**
     * @Author: Yifan
     * @Date: 2019/5/30 16:40
     * @Description: 发送消息
     */
    @Override
    public void sendMessage(String exchange, String routerkey, RabbitMessage message) {
        messageProducer.sendMessage(exchange, routerkey, message);
    }

}
