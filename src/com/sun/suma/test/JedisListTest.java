package com.sun.suma.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import redis.clients.jedis.Jedis;

public class JedisListTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis=new Jedis("localhost");
		List<User> usersList=new ArrayList<User>();
		User user1=new User();
		user1.setName("zhangpan");
		User user2=new User();
		user2.setName("wangsan");
		usersList.add(user1);
		usersList.add(user2);
		String key="user"+new Random(1000).nextInt();
		byte[] temp=ObjectsTranscoder.serialize(usersList);
		jedis.set(key.getBytes(), temp);
		byte []in=jedis.get(key.getBytes());
		List<User> list=ObjectsTranscoder.deserialize(in);
		for(User user:list)
		{
			System.out.println("User name is"+"  "+user.getName());
		}
		
	}
	
	static class ObjectsTranscoder{
		
		public static  byte[] serialize(List<User> value)
		{
			if(value==null)
			{
				throw new NullPointerException("Can not serialize null");
			}
			byte[]rv=null;
			ByteArrayOutputStream bos=null;
			ObjectOutputStream os=null;
			try {
				bos=new ByteArrayOutputStream();
				os=new ObjectOutputStream(bos);
				for(User user:value)
				{
					os.writeObject(user);
				}
				os.writeObject(null);
				os.close();
				bos.close();
				rv=bos.toByteArray();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				close(os);
				close(bos);
			}
			return rv;
		}
		
		public static List<User> deserialize(byte[]in)
		{
			List<User> users=new ArrayList<User>();
			ByteArrayInputStream bInputStream=null;
			ObjectInputStream oInputStream=null;
			try {
				if(in!=null)
				{
					bInputStream=new ByteArrayInputStream(in);
					oInputStream=new ObjectInputStream(bInputStream);
					while (true) {
						User user=(User)oInputStream.readObject();
						if(user==null)
						{
							break;
						}else {
							users.add(user);
						}
						
					}
					bInputStream.close();
					oInputStream.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return users;
		}
		
	}
	public static void close(Closeable closeable) {  
        if (closeable != null) {  
            try {  
                closeable.close();  
            } catch (Exception e) {  
                
            }  
        }  
    } 

}
