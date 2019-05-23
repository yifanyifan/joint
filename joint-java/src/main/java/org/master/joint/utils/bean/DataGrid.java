package org.master.joint.utils.bean;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yifan
 * @Date: 2019/5/7 13:37
 * @Description: DataGrid 数据返回模型
 */
@Data
public class DataGrid<T> implements Serializable {
    /**
     * 单个数据
     */
    private T obj;
    /**
     * 是否有效
     */
    private boolean flag;
    /**
     * 消息
     */
    private String msg;
    /**
     * 编码
     */
    private String code;
    /**
     * 数量
     */
    private Long total;
    /**
     * 所有数据
     */
    private List<T> rows = Lists.newArrayList();
}
