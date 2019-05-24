package org.master.joint.entity.demo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.master.joint.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/24
 * Modified By:
 */
@Entity
@Table(name = "test_demo")
@ApiModel(value = "TestDemo", description = "测试类")
@Data
public class TestDemo extends BaseEntity {
    private String name;
}
