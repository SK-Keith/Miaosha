package com.keith.miaosha.redis;

/**
 * @author YMX
 * @date 2019/1/1 12:29
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;//

    public BasePrefix(String prefix) {//0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    //过期时间
    @Override
    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    //获得前缀
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
