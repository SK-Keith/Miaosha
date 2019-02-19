package com.keith.miaosha.redis;

/**
 * key的前缀
 * @author YMX
 * @date 2019/1/1 12:28
 */
public interface KeyPrefix {

    public int expireSeconds();//有效期

    public String getPrefix();//前缀
}
