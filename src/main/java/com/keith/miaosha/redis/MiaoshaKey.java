package com.keith.miaosha.redis;

/**
 * @author YMX
 * @date 2019/4/18 15:59
 */
public class MiaoshaKey extends BasePrefix{

    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");

}
