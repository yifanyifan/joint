package org.master.joint.controller.testdemo;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.master.joint.bean.DataGrid;
import org.master.joint.bean.Version;
import org.master.joint.dto.airwallex.AccountRedis;
import org.master.joint.entity.demo.TestDemo;
import org.master.joint.rabbit.RabbitMessage;
import org.master.joint.rabbit.RabbitSetConstant;
import org.master.joint.service.RabbitMessageServiceI;
import org.master.joint.service.RedisHashService;
import org.master.joint.service.TestDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/24
 * Modified By:
 */
@RestController
@RequestMapping(Version.VERSION + "/testdemo")
@Slf4j
public class TestDemoController {
    private static Logger logger = LoggerFactory.getLogger(TestDemoController.class);

    @Resource
    private TestDemoService testDemoService;

    @Reference
    private RedisHashService redisHashService;

    @Reference
    private RabbitMessageServiceI rabbitMessageServiceI;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public DataGrid save(@RequestBody TestDemo testDemo) {
        DataGrid dataGrid = new DataGrid();

        try {
            testDemoService.save(testDemo);

            dataGrid.setFlag(true);
            dataGrid.setMsg("新增成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            dataGrid.setMsg("新增失败");
        }

        return dataGrid;
    }

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public DataGrid sendMsg() {
        DataGrid dataGrid = new DataGrid();

        Map map = new HashMap();
        map.put("a", "b");
        RabbitMessage rabbitMessage = new RabbitMessage("test", "11111", map);

        rabbitMessageServiceI.sendMessage(RabbitSetConstant.TEST_DIRECT_EXCHANGE, RabbitSetConstant.TEST_ROUTING_KEY, rabbitMessage);

        return dataGrid;
    }

    @RequestMapping(value = "/redisSendMsg", method = RequestMethod.POST)
    public DataGrid redisSendMsg() {
        DataGrid dataGrid = new DataGrid();

        AccountRedis accountRedis = new AccountRedis();
        accountRedis.setId("11111111");
        accountRedis.setEmail("222222222222");

        redisHashService.put("AirwallexAccount", "1111", accountRedis);

        return dataGrid;
    }
}
