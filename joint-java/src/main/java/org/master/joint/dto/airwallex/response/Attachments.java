/**
 * Copyright 2019 bejson.com
 */
package org.master.joint.dto.airwallex.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: Yifan
 * @Date: 2019/5/24 11:37
 * @Description: 文件列表
 */
@Data
public class Attachments {
    private List<String> identity_files;
}