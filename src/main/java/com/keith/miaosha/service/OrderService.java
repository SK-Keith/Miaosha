package com.keith.miaosha.service;

import com.keith.miaosha.dao.OrderDao;
import com.keith.miaosha.domain.MiaoshaOrder;
import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.domain.OrderInfo;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author YMX
 * @date 2019/2/15 23:15
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId){
        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods){
        //下订单，即记录秒杀该商品的用户信息及商品的信息，返回出一个订单id，方便记录到秒杀订单表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);//收货地址
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insert(orderInfo);
        //写入秒杀订单表，理解为业务需要有这个秒杀订单表，不同于订单表，这张表只记录了用户id,订单id，商品id
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
