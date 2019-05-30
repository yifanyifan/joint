package org.master.common.service.rabbit;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Yifan
 * @Date: 2019/5/30 16:51
 * @Description: 发送消息
 */
@Component
@Slf4j
public class MessageProducer implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {
    private static Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routerKey, Object message) {
        logger.info("开始发送消息,内容为:{}", JSON.toJSONString(message));

        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(this);
        this.rabbitTemplate.convertAndSend(exchange, routerKey, message);
    }

    /**
     * 回调函数: 消息是否到达Exchange确认
     */
    @Override
    public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("### 未找到Queue，Exchange:{} / RoutingKey:{}", exchange, routingKey);
    }

    /**
     * 回调函数：消息是否由Exchange到达Queue（到达则不回调）
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息到达Exchange");
        if (!ack) {
            logger.info("### 消息未到达Exchange");
        }
    }
}