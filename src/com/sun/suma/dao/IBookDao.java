package com.sun.suma.dao;

import java.util.List;

import com.sun.suma.entity.Book;

public interface IBookDao {
	

	public Book selectById(Integer id);

	public Integer deleteById(Integer id);
	

	public Integer insert(Book book);
	

	public Book selectBookWithUserById(Integer id);

	public Integer insertBatch(List<Book> books);

}
