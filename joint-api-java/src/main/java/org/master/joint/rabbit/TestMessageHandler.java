package org.master.joint.rabbit;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/30
 * Modified By:
 */
@Component
@Slf4j
public class TestMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(TestMessageHandler.class);

    @RabbitListener(queues = RabbitSetConstant.TEST_QUEUE)
    public void cc(RabbitMessage rabbitMessage, Message message, Channel channel) throws Exception {
        logger.info("接收消息：这个message已收到");

        try {
            logger.info("接收消息：处理完成 ");

            //false只确认当前一个消息收到，true确认所有consumer获得的消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            logger.info("### 消息处理异常, 消息内容是：{}", JSON.toJSONString(rabbitMessage));
            logger.info(e.getMessage(), e);
            //重新放入队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            //抛弃此条消息
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}

