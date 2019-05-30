package org.master.joint.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/30
 * Modified By:
 */
@Component
public class TestMessageHandler {
    @RabbitListener(queues = RabbitKeyConstant.TEST_QUEUE)
    public void cc(RabbitMessage message) {
        System.out.println("1111");
        System.out.println("2222");
    }
}

