package org.master.joint.rabbit;

/**
 * @Author: Yifan
 * @Date: 2019/5/30 20:13
 * @Description: MQ Exchange/Queue/Routing 名称定义集合
 */
public class RabbitSetConstant {
    /**
     * 测试队列名
     */
    public static final String TEST_QUEUE = "testQueue";

    /**
     * 测试交换机
     */
    public static final String TEST_DIRECT_EXCHANGE = "testDirectExchange";

    /**
     * 测试任务队列QueueKey
     */
    public static final String TEST_ROUTING_KEY = "testRoutingKey";
}
