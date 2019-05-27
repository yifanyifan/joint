package org.master.joint.controller.testdemo;

import lombok.extern.slf4j.Slf4j;
import org.master.joint.bean.DataGrid;
import org.master.joint.bean.Version;
import org.master.joint.entity.demo.TestDemo;
import org.master.joint.service.TestDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
