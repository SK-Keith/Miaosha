package com.keith.miaosha.service;

import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.domain.OrderInfo;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YMX
 * @date 2019/2/15 23:15
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){
        //减库存  下订单   写入秒杀订单
        goodsService.reduceStock(goodsVo);

        return orderService.createOrder(user, goodsVo);
    }

}
