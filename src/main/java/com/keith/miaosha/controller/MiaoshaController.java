package com.keith.miaosha.controller;

import com.keith.miaosha.domain.*;
import com.keith.miaosha.rabbitmq.MQSender;
import com.keith.miaosha.rabbitmq.MiaoshaMessage;
import com.keith.miaosha.redis.GoodsKey;
import com.keith.miaosha.redis.MiaoshaKey;
import com.keith.miaosha.redis.OrderKey;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.result.CodeMsg;
import com.keith.miaosha.result.Result;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.MiaoshaService;
import com.keith.miaosha.service.MiaoshaUserService;
import com.keith.miaosha.service.OrderService;
import com.keith.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * @author YMX
 * @date 2019/2/15 23:11
 */
@Controller
@RequestMapping(value = "/miaosha")
public class MiaoshaController implements InitializingBean{

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null){
            return ;
        }
        for (GoodsVo goodsVo: goodsList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(),false);
        }
    }

    /**
     * 172.2/sec
     * 415.5/sec
     * 秒杀接口返回单纯页面请求的结果数据，而非整个页面，实现前后端分离
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model,MiaoshaUser miaoshaUser,
                                     @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", miaoshaUser);
        //判断用户
        if (miaoshaUser == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if (stock < 0){
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(),goodsId);
        if (order != null){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setUser(miaoshaUser);
        mm.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(mm);

        //向客户端返回0，表示正在排队
        return Result.success(0);
    }

    /**
     * 前端轮询查看是否下单成功
     *  查询该用户，该商品的数据库信息（redis）
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser miaoshaUser,@RequestParam("goodsId")long goodsId){
        model.addAttribute("user", miaoshaUser);
        if (miaoshaUser == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(miaoshaUser.getId(),goodsId);
        return Result.success(result);
    }

    @RequestMapping(value = "/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model){
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        for (GoodsVo goodsVo : goodsVoList){
            goodsVo.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goodsVo.getId(),10);
            localOverMap.put(goodsVo.getId(),false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsVoList);
        return Result.success(true);
    }


}
