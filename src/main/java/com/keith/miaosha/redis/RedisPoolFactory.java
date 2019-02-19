package com.keith.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author YMX
 * @date 2019/1/1 12:20
 */
@Service
public class RedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0);
//        JedisPool jedisPool = new JedisPool(poolConfig,redisConfig.getHost(),
//                redisConfig.getTimeout() * 1000, Boolean.parseBoolean(redisConfig.getPassword()));
        return jp;
//        JedisPool jedisPool = new JedisPool(poolConfig,redisConfig.getHost()getHost,
//                getHostredisConfig.getTimeout() * 1000, redisConfig.getPassword());
    }
}
