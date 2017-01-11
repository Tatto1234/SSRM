package com.sun.suma.serviceimpl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sun.suma.dao.IBookDao;
import com.sun.suma.entity.Book;
import com.sun.suma.service.IBookService;

@Service("bookService")
public class BookServiceImpl  implements IBookService{

	
	@Resource
	private IBookDao bookDao;

	private RedisTemplate<Serializable, Serializable> redisTemplate;
	public Book selectBookById(Integer id) {
		// TODO Auto-generated method stub
		return this.bookDao.selectById(id);
	}

	@Override
	public Integer deleteBookById(Integer id) {
		// TODO Auto-generated method stub
		return this.bookDao.deleteById(id);
	}

	@Override
	public Integer insertBook(Book book) {
		// TODO Auto-generated method stub
		return this.bookDao.insert(book);
	}

	@Override
	public Book selectBookWithUserById(Integer id) {
		// TODO Auto-generated method stub
		return  this.bookDao.selectBookWithUserById(id);
	}

	@Override
	public Integer insertBatchBooks(List<Book> books) {
		// TODO Auto-generated method stub
		return this.bookDao.insertBatch(books);
	}

	@Override
	public void addEntity(final Book book) {
		// TODO Auto-generated method stub
		redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(book.getClass().getName() + "_" + 100),
                               redisTemplate.getStringSerializer().serialize(book.toString()));
				return connection;
                
            }
        });
	}

}
