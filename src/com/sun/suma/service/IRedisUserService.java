package com.sun.suma.service;

import com.sun.suma.entity.RedisUser;

public interface IRedisUserService {
	
	public Integer addUser(RedisUser user);
	
	public Integer delete(RedisUser user);

}
