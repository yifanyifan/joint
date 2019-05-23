package org.master.joint.service;

import java.util.List;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/23
 * Modified By:
 */
public interface RedisHashService {

    boolean hasKey(String key, String field);

    /**
     * 设置值
     *
     * @param key   hash的key
     * @param field hash里面的字段名
     * @param value hash里面的字段的值
     */
    void put(String key, String field, Object value);

    /**
     * 获取hash中的字段值
     */
    <T> T get(String key, String field, Class<T> clazz);

    /**
     * 获取hash中全部value
     */
    <T> List<T> getValues(String key, Class<T> clazz);

    /**
     * 删除hash中的某字段
     */
    void remove(String key, String field);

    /**
     * 删除hash
     */
    void deleteHash(String key);
}
