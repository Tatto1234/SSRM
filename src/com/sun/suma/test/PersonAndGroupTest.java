package com.sun.suma.test;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.suma.entity.Person;

public class PersonAndGroupTest {
	
	private final static String config_XML="spring-mybatis.xml";
	private static SqlSession session;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd k:mm:ss");
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		  InputStream is = PersonAndGroupTest.class.getClassLoader().getResourceAsStream(config_XML);
		  SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		  session = sessionFactory.openSession();
		  
	}
	@Test
	public void savepersonTest() {
		Person person=new Person();
		person.setName("zhangpan");
		person.setPassword("123");
		session.insert("com.sun.suma.entity.Person",person);
	}

}
