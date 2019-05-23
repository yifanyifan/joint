/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.accountscreate.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.master.joint.enums.IdentityFilesTagEnum;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 10:44
 * @Description: 单个FileID
 */
@Data
@ApiModel(value = "Identity_files", description = "单个FileID")
public class Identity_files {
    @ApiModelProperty(name = "description", value = "文件名称")
    private String description;

    @ApiModelProperty(name = "file_id", value = "文件ID")
    private String file_id;

    @ApiModelProperty(name = "tag", value = "营业执照File上传ID集合")
    private IdentityFilesTagEnum tag;
}