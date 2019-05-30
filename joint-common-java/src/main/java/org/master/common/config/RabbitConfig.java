package org.master.common.config;

import org.master.joint.rabbit.RabbitSetConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/30
 * Modified By:
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue testQueue() {
        return new Queue(RabbitSetConstant.TEST_QUEUE, true);
    }

    @Bean
    DirectExchange testDirectExchange() {
        return new DirectExchange(RabbitSetConstant.TEST_DIRECT_EXCHANGE);
    }

    @Bean
    Binding bindingExchangeMessage(Queue testQueue, DirectExchange testDirectExchange) {
        return BindingBuilder.bind(testQueue).to(testDirectExchange).with(RabbitSetConstant.TEST_ROUTING_KEY);
    }
}
