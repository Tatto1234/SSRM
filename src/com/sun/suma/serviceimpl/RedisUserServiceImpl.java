package com.sun.suma.serviceimpl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.sun.suma.entity.RedisUser;
import com.sun.suma.service.IRedisUserService;

@Service
public class RedisUserServiceImpl  implements IRedisUserService{
	
//	@Resource
//	private RedisTemplate<String, RedisUser> redisTemplate;
	
	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redisTemplate;

	public void testSet() {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection redisConnection) {
				redisConnection.hSet("tempKey".getBytes(), "hello".getBytes(), "world".getBytes());
				
				return true;
			}
		});
	}
	
	@Override
	public Integer addUser(RedisUser user) {
		// TODO Auto-generated method stub
		ValueOperations<String, RedisUser> valueOperations=redisTemplate.opsForValue();
		valueOperations.set("sumadv4", user);
		
		return null;
	}

	@Override
	public Integer delete(RedisUser user) {
		//valueOperations.
		return null;
	}

}
