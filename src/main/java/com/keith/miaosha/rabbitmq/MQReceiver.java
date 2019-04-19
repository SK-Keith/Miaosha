package com.keith.miaosha.rabbitmq;

import com.keith.miaosha.domain.MiaoshaOrder;
import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.MiaoshaService;
import com.keith.miaosha.service.OrderService;
import com.keith.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YMX
 * @date 2019/4/17 14:08
 */
@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    /**
     * 使用Direct Exchange类型
     */
    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
        MiaoshaMessage mm = RedisService.stringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock < 0 ){
            return ;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null){
            return ;
        }
        //减库存，下订单，写入秒杀订单
        miaoshaService.miaosha(user, goodsVo);
    }

    /**
     * Direct Exchange
     */
//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void receive(String message){
//        log.info("receive message:" + message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveTopic1(String message){
//        log.info("topic queue1 message: "+message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveTopic2(String message){
//        log.info("topic queue2 message: "+message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADER_QUEUE)
//    public void receiveHeaderQueue(byte[] message){
//        log.info("header queue message:" + new String(message));
//    }
}
