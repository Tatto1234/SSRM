package com.sun.suma.dao;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.sun.suma.entity.RedisUser;

public class IRedis {
	
	private RedisTemplate<String, RedisUser> redisTemplate;
	
	public Integer addUser(RedisUser user) {
		ValueOperations<String, RedisUser> valueOperations=redisTemplate.opsForValue();
		valueOperations.set("sumadv4", user);
		return null;
	}
	
	public RedisUser getUser(int userId) {
		return redisTemplate.opsForValue().get("sumadv4");
	}
}
