package com.sun.suma.test;

import redis.clients.jedis.Jedis;

public class testJedis {
    public static void main(String[] args) {
        
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("java", "http://www.baidu.com");
        String value = jedis.get("java");
        System.out.println(value);
 
    }
}
