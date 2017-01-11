package com.sun.suma.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.suma.entity.RedisUser;
import com.sun.suma.service.IRedisUserService;

@Controller
@RequestMapping("/redis")
public class RedisUserController {
	
	@Autowired
	private IRedisUserService redisService;
	
	/**
	 * 添加redisUser
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addRedisUser")
	public void addRedisUser(HttpServletRequest request,HttpServletResponse response) 
	{
		
		RedisUser user=new RedisUser();
		user.setAddress("haidian");
		user.setCount(100);
		user.setName("zhangshidan");
		int i = redisService.addUser(user);
		
	}

}
