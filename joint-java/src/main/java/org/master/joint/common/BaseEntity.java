package org.master.joint.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@ApiModel(value = "BaseEntity", description = "实体父类")
@MappedSuperclass
public abstract class BaseEntity {

    private final static Logger log = LoggerFactory.getLogger(BaseEntity.class);

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", nullable = false, length = 32)
    @ApiModelProperty(hidden = true)
    protected String id;

    @ApiModelProperty(hidden = true)
    @Column(name = "is_deleted", columnDefinition = "bit default 0")
    protected boolean deleted;

    @ApiModelProperty(hidden = true)
    @Column(name = "created_date", nullable = true, updatable = false, columnDefinition = "datetime(3) default now(3)")
    protected Date createdDate;

    @ApiModelProperty(hidden = true)
    @Column(name = "LAST_UPDATED_DATE", columnDefinition = "datetime(3) default now(3)")
    protected Date lastUpdatedDate;

}
