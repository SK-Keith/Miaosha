package com.keith.miaosha.controller;

import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.domain.OrderInfo;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.result.CodeMsg;
import com.keith.miaosha.result.Result;
import com.keith.miaosha.service.GoodsService;
import com.keith.miaosha.service.OrderService;
import com.keith.miaosha.service.UserService;
import com.keith.miaosha.vo.GoodsVo;
import com.keith.miaosha.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/detail")
	@ResponseBody
	public Result<OrderDetailVo> info(MiaoshaUser miaoshaUser,
									  @RequestParam("orderId") long orderId){
		if (miaoshaUser == null){
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		OrderInfo orderInfo = orderService.getOrderById(orderId);
		if (orderInfo == null){
			return Result.error(CodeMsg.ORDER_NOT_EXIST);
		}
		long goodsId = orderInfo.getGoodsId();
		GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
		OrderDetailVo orderDetailVo = new OrderDetailVo();
		orderDetailVo.setOrderInfo(orderInfo);
		orderDetailVo.setGoodsVo(goodsVo);
		return Result.success(orderDetailVo);
	}
}
