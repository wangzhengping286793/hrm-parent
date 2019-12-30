package cn.itsource.hrm.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Scope("singleton")
public class RedisUtils {

    private String host="127.0.0.1";
    private Integer port=6379;
    private String password="itsource";
    private Integer maxIbdel=1;
    private Integer maxTotal=11;
    private Long maxwaitMills=10*1000L;
    private Boolean testOnBorrw=true;
    JedisPool jedisPool=null;

    public RedisUtils(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIbdel);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxwaitMills);
        config.setTestOnBorrow(testOnBorrw);
        jedisPool = new JedisPool(config, host, port, maxwaitMills.intValue(), password);
    }

    //获取连接
    public Jedis getSource(){
        return jedisPool.getResource();
    }
    //关闭资源
    public void closeResource(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }
    //设置字符集
    public void set(String key, String value){
        Jedis jedis=getSource();
        jedis.set(key,value );
        closeResource(jedis);
    }

    //设置字符集
    public void set(byte[]key,byte[]value){
        Jedis jedis = getSource();
        jedis.set(key,value );
        closeResource(jedis);
    }

    //得到字符集
    public byte[] get(byte[] key){
        Jedis jedis = getSource();
        try {
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeResource(jedis);
        }
        return null;
    }

    //得到字符集
    public String get(String key){
        Jedis jedis = getSource();
        try {
            return jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeResource(jedis);
        }
        return null;
    }
}
