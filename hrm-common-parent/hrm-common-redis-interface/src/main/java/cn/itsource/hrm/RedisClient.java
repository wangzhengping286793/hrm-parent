package cn.itsource.hrm;

import cn.itsource.hrm.client.Impl.RedisClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "REDIS-SERVICE",path ="/redis",fallback = RedisClientFallback.class)
public interface RedisClient {

    @PostMapping("/set")
    public void set(@RequestParam String key, @RequestParam String value);

    @GetMapping("/get")
    public String get(@RequestParam String key);
}
