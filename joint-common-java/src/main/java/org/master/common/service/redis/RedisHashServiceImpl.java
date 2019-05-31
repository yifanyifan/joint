package org.master.common.service.redis;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.master.joint.service.RedisHashService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisHashServiceImpl implements RedisHashService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 判断key是否存在
     *
     * @param key   hash的key
     * @param field hash里面的字段名
     */
    @Override
    public boolean hasKey(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 设置值
     *
     * @param key   hash的key
     * @param field hash里面的字段名
     * @param value hash里面的字段的值
     */
    @Override
    public void put(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, JSONObject.toJSONString(value));
    }

    /**
     * 获取hash中的字段值
     *
     * @param key
     * @param field
     * @param clazz
     */
    @Override
    public <T> T get(String key, String field, Class<T> clazz) {
        Object value = redisTemplate.opsForHash().get(key, field);
        if (value == null) {
            return null;
        }
        return JSONObject.parseObject((String) value, clazz);
    }

    /**
     * 获取hash中全部value
     *
     * @param key
     * @param clazz
     */
    @Override
    public <T> List<T> getValues(String key, Class<T> clazz) {
        List<Object> values = redisTemplate.opsForHash().values(key);
        List<T> objs = Lists.newArrayList();
        values.stream().forEach(value -> objs.add(JSONObject.parseObject((String) value, clazz)));
        return objs;
    }

    /**
     * 删除hash中的某字段
     *
     * @param key
     * @param field
     */
    @Override
    public void remove(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 删除hash
     */
    @Override
    public void deleteHash(String key) {
        redisTemplate.delete(key);
    }
}
