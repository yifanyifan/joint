package org.master.joint.entity.demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.master.joint.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

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
    @ApiModelProperty(name = "name", value = "名称")
    @Column(name = "name", length = 32, nullable = true)
    private String name;

    @ApiModelProperty(name = "amount", value = "金额")
    @Column(name = "amount", columnDefinition = "decimal(17,2) default '0.00'")
    private BigDecimal amount = BigDecimal.ZERO;
}
