/**
  * Copyright 2019 bejson.com 
  */
package org.master.joint.dto.airwallex.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 9:36
 * @Description: 身份证正、反面File上传ID集合
 */
@Data
@ApiModel(value = "Attachments2", description = "身份证正、反面File上传ID集合")
public class Attachments2 {
    @ApiModelProperty(name = "identity_files", value = "File上传ID集合")
    private List<Identity_files> identity_files;
}