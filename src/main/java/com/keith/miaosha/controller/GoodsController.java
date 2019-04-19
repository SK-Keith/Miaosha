package com.keith.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.keith.miaosha.domain.Goods;
import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.redis.GoodsKey;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.result.Result;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.MiaoshaUserService;
import com.keith.miaosha.vo.GoodsDetailVo;
import com.keith.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value="/to_list")
    @ResponseBody
    public Result<List<GoodsVo>> toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        //取缓存，实际开发中，这个功能是分页效果的，大部分用户也只是点了前一两页就可以了，但是失效是自动失效，不用用户自己干预
//        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
//        if(!StringUtils.isEmpty(html)) {
//            return html;
//        }
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
//        model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";
//        SpringWebContext ctx = new SpringWebContext(request,response,
//                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
        //手动渲染
//        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
//        if(!StringUtils.isEmpty(html)) {
//            redisService.set(GoodsKey.getGoodsList, "", html);
//        }
//        return html;
        return Result.success(goodsList);
    }


    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> detail(MiaoshaUser user,@PathVariable("goodsId")long goodsId){
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
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
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }

}
