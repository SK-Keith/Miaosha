package com.keith.miaosha.vo;

import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.domain.OrderInfo;

/**
 * @author YMX
 * @date 2019/3/25 11:05
 */
public class OrderDetailVo {
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}
