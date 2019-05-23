package org.master.joint.vo.airwallex;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.master.joint.dto.airwallex.accountscreate.response.Account_details;
import org.master.joint.dto.airwallex.accountscreate.response.Primary_contact;

import java.util.Date;

/**
 * @Author: Yifan
 * @Date: 2019/5/8 11:01
 * @Description: 接口响应参数
 */
@Data
@Api(value = "AirWallexResponseVO", tags = "接口响应参数")
public class AirWallexResponseVO {
    @ApiModelProperty(name = "account_details", value = "详细描述")
    private Account_details account_details;

    @ApiModelProperty(name = "created_at", value = "子账户添加时间")
    private Date created_at;

    @ApiModelProperty(name = "id", value = "子账户添加返回ID")
    private String id;

    @ApiModelProperty(name = "primary_contact", value = "子账户")
    private Primary_contact primary_contact;

    @ApiModelProperty(name = "status", value = "状态")
    private String status;

    @ApiModelProperty(name = "token", value = "令牌")
    private String token;

    @ApiModelProperty(name = "created", value = "创建时间")
    private Long created;

    @ApiModelProperty(name = "file_id", value = "文件ID")
    private String file_id;

    @ApiModelProperty(name = "filename", value = "文件名称")
    private String filename;

    @ApiModelProperty(name = "object_type", value = "文件类型")
    private String object_type;

    @ApiModelProperty(name = "size", value = "文件大小")
    private Integer size;

    @ApiModelProperty(name = "message", value = "信息")
    private String message;
}
