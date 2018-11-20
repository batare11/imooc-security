package com.imooc.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;

import io.swagger.annotations.ApiParam;

@RestController
public class UserController {
   
	@RequestMapping(value="/user" ,method=RequestMethod.GET)
	@JsonView(User.UserSimpleView.class)
	public List<User> query(UserQueryCondition userQueryCondition,@PageableDefault(page=3,size=10,sort="username,asc") Pageable pageable) {
		
		System.out.println(ReflectionToStringBuilder.toString(userQueryCondition,ToStringStyle.MULTI_LINE_STYLE));
		
		System.out.println(pageable.getPageNumber());
		System.out.println(pageable.getPageSize());
		System.out.println(pageable.getSort());
		
		 List<User> users = new ArrayList<>();
		 users.add(new User());
		 users.add(new User());
		 users.add(new User());
		return users;
	}
	
	@RequestMapping(value = "/user/{id:\\d+}",method=RequestMethod.GET)
	@JsonView(User.UesrDetailView.class)
	@ApiParam("用户id")
	public User getInfo (@PathVariable String id) {
		System.out.println("进入getInfo服务");
		User user = new User ();
		user.setUsername("tom");
		return user;
	}
}
