package cn.itsource.hrm.client.Impl;

import cn.itsource.hrm.RedisClient;
import org.springframework.stereotype.Component;

@Component
public class RedisClientFallback implements RedisClient {
    public void set(String key, String value) {

    }

    public String get(String key) {
        return "获取失败";
    }
}
