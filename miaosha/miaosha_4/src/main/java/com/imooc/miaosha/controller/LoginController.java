package com.imooc.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {
   private static Logger log = LoggerFactory.getLogger(LoginController.class);
   @Autowired
   UserService userService;
   @Autowired
   RedisService redisService;
   @Autowired
   MiaoshaUserService miaoshaUserService;
  @RequestMapping("/to_login")
  public String toLogin(){
	  return "login";
  }
  @RequestMapping("/do_login")
  @ResponseBody
  public Result<Boolean> doLogin(@Valid LoginVo loginVo,HttpServletResponse response){
	  log.info(loginVo.toString());
	  //参数校验   后续被注解替换
//	  String passInput = loginVo.getPassword();
//	  String mobile = loginVo.getMobile();
//	  if(StringUtils.isEmpty(passInput)){
//		  return Result.error(CodeMsg.PASSWORD_EMPTY);
//	  }
//	  if(StringUtils.isEmpty(mobile)){
//		  return Result.error(CodeMsg.MOBILE_EMPTY);
//	  }
//	  if(!ValidatorUtil.isMobile(mobile)){
//		  return Result.error(CodeMsg.MOBILE_ERROR);
//	  }
	  //登录
	 miaoshaUserService.login(response,loginVo);
	 return Result.success(true);
  }
}