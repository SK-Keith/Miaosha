package com.keith.miaosha.rabbitmq;

import com.keith.miaosha.domain.MiaoshaUser;

/**
 * @author YMX
 * @date 2019/4/18 15:24
 */
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
