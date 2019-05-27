package org.master.joint.service.impl;

import org.master.joint.entity.demo.TestDemo;
import org.master.joint.repository.testdemo.TestDemoDao;
import org.master.joint.service.TestDemoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/24
 * Modified By:
 */
@Transactional
@Service
public class TestDemoServiceImpl implements TestDemoService {
    @Resource
    private TestDemoDao testDemoDao;

    @Override
    public void save(TestDemo testDemo) {
        testDemoDao.save(testDemo);
    }
}
