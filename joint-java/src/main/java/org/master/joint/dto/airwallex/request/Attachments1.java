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
 * @Description: 营业执照File上传ID集合
 */
@Data
@ApiModel(value = "Attachments1", description = "营业执照File上传ID集合")
public class Attachments1 {
    @ApiModelProperty(name = "business_documents", value = "营业执照File上传ID集合")
    private List<Identity_files> business_documents;
}