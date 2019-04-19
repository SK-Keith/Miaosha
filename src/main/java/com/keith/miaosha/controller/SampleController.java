package com.keith.miaosha.controller;

import com.keith.miaosha.domain.User;
import com.keith.miaosha.rabbitmq.MQSender;
import com.keith.miaosha.redis.RedisService;
import com.keith.miaosha.redis.UserKey;
import com.keith.miaosha.result.CodeMsg;
import com.keith.miaosha.result.Result;
import com.keith.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User类和redis之间的操作
 * @author YMX
 * @date 2018/12/29 11:32
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> home() {
        return Result.success("Hello，world");
    }

//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//        sender.send("hello,keith");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> mqTopic() {
//        sender.sendTopic("hello,keith");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> mqFanout() {
//        sender.sendFanout("hello,keith");
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/mq/header")
//    @ResponseBody
//    public Result<String> mqHeader() {
//        sender.sendHeader("hello,keith");
//        return Result.success("Hello，world");
//    }

    @RequestMapping("/error")
    @ResponseBody
    public Result<String> error() {
        return Result.error(CodeMsg.SESSION_ERROR);
    }

    @RequestMapping("/hello/themaleaf")
    public String themaleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    //1代表id为1的数据
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> get() {
        System.out.println("UserKey.getById:"+UserKey.getById);
        User user = redisService.get(UserKey.getById,""+2,User.class);
        return Result.success(user);
    }
    
    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> set() {
    	User user = new User();
    	user.setId(2);
    	user.setName("keithSet");
        Boolean b = redisService.set(UserKey.getById,""+2,user);
        return Result.success(b);
    }

    //删除用户
    @RequestMapping("/redis/del")
    @ResponseBody
    public Result<Boolean> del(){
        boolean b = redisService.delete(UserKey.getById,""+2);
        return Result.success(b);
    }
    
}
