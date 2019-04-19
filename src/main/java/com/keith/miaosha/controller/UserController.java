package com.keith.miaosha.controller;

import com.keith.miaosha.domain.MiaoshaUser;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.result.Result;
import com.keith.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author YMX
 * @date 2019/1/2 20:33
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user){
        return Result.success(user);
    }

}
