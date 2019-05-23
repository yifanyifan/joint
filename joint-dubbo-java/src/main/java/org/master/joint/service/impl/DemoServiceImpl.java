package org.master.joint.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.master.joint.service.DemoService;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/21
 * Modified By:
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "111111111111";
    }
}
