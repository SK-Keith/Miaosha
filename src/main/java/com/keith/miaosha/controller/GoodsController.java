package com.keith.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.MiaoshaUserService;
import com.keith.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author YMX
 * @date 2019/1/2 20:33
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

//    @RequestMapping("/to_list")
//    public String toLogin(Model model, @CookieValue(value=MiaoshaUserService.COOKI_NAME_TOKEN,required = false)
//            String cookieToken, @RequestParam(value=MiaoshaUserService.COOKI_NAME_TOKEN,required = false)String paramToken) {
//        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user = userService.getByToken(token);
//        model.addAttribute("user",user);//user可能不存在
////        model.addAttribute("user",user);
//        return "goods_list";
//    }

    @RequestMapping("/to_list")
    public String toLogin(Model model,MiaoshaUser user){
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user,
                         @PathVariable("goodsId")long goodsId){
        model.addAttribute("user",user);

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);

        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt){//秒杀还没开始
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now)/1000);
        }else if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else{//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }

}
