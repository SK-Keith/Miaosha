package com.keith.miaosha.controller;

import com.keith.miaosha.domain.*;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.result.CodeMsg;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.MiaoshaService;
import com.keith.miaosha.service.MiaoshaUserService;
import com.keith.miaosha.service.OrderService;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author YMX
 * @date 2019/2/15 23:11
 */
@Controller
@RequestMapping(value = "/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha")
    public String list(Model model, MiaoshaUser miaoshaUser,
                       @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",miaoshaUser);
        if (miaoshaUser == null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock < 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
        }
        //判断是否已经秒杀过了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);
        if (order != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";
    }
}
