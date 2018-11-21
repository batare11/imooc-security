package com.imooc.miaosha.controller;

import java.sql.SQLException;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;

/**
 * 
 * @author 35-pxiaodong
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
  
	@Autowired
	UserService userService;
	@Autowired
	RedisService redisService;
	
     @RequestMapping("/")
     @ResponseBody
     String home(){
    	 return "Hello World!";
     }
     @RequestMapping("/hello")
     @ResponseBody
     Result<String> hello(){
    	return Result.success("imooc.success");
     }
     @RequestMapping("/helloError")
     @ResponseBody
     Result<String> helloError(){
    	return Result.error(CodeMsg.SERVER_ERROR);
     }
     @RequestMapping("/thymeleaf")
     String thymeleaf(Model model){
    	model.addAttribute("name", "Batare");
    	return "hello";
     }
     @RequestMapping("/db/get")
     @ResponseBody
     Result<User> dbGet(Model model){
    	User user = userService.getById(1);
    	return Result.success(user);
     }
     //事务
     @RequestMapping("/db/tx")
     @ResponseBody
     Result<Boolean> dbTx(Model model){
    	userService.tx();
    	return Result.success(true);
     }
     //事务
     @RequestMapping("redis/get")
     @ResponseBody
     Result<User> redisGet(){
    	User user = redisService.get(UserKey.getById,""+1,User.class);
    	return Result.success(user);
     }
     @RequestMapping("redis/set")
     @ResponseBody
     Result<Boolean> redisSet(){
    	 User user = new User();
    	 user.setId(1);
    	 user.setName("111");
    	 redisService.set(UserKey.getById,""+1,user);
    	return Result.success(true);
     }
}
