package com.keith.miaosha.service;

import com.keith.miaosha.domain.MiaoshaOrder;
import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.domain.OrderInfo;
import com.keith.miaosha.redis.MiaoshaKey;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    RedisService redisService;

    //减库存  下订单   写入秒杀订单
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo){
        //为了防止库存数量减为负数，在执行数据库减库存操作时，将数据库语句加上更新时条件商品数量大于零
        boolean success = goodsService.reduceStock(goodsVo);
        if (success){
            return orderService.createOrder(user, goodsVo);
        }else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }



    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
    }

    public void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver,""+goodsId, true);
    }

    public void reset(List<GoodsVo> goodsVoList){
        goodsService.resetStock(goodsVoList);
        orderService.deleteOrders();
    }
}
