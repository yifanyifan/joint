package org.master.common.servicedubbo.redis;

import com.alibaba.dubbo.config.annotation.Service;
import org.master.common.service.redis.RedisHashServiceImpl;
import org.master.joint.service.RedisHashService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Yifan
 * @Description:
 * @date: 2019/5/23
 * Modified By:
 */
@Service
public class RedisHashDubboService implements RedisHashService {
    @Resource
    private RedisHashServiceImpl redisHashService;

    @Override
    public boolean hasKey(String key, String field) {
        return redisHashService.hasKey(key, field);
    }

    @Override
    public void put(String key, String field, Object value) {
        redisHashService.put(key, field, value);
    }

    @Override
    public <T> T get(String key, String field, Class<T> clazz) {
        return redisHashService.get(key, field, clazz);
    }

    @Override
    public <T> List<T> getValues(String key, Class<T> clazz) {
        return redisHashService.getValues(key, clazz);
    }

    @Override
    public void remove(String key, String field) {
        redisHashService.remove(key, field);
    }

    @Override
    public void deleteHash(String key) {
        redisHashService.deleteHash(key);
    }
}
