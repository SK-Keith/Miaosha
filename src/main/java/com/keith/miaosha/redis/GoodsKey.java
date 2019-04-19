package com.keith.miaosha.redis;

import com.keith.miaosha.domain.Goods;

/**
 * @author YMX
 * @date 2019/3/14 23:25
 */
public class GoodsKey extends BasePrefix{
    private GoodsKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");

}
