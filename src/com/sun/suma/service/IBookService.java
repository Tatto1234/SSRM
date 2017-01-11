package com.sun.suma.service;

import java.util.List;

import com.sun.suma.entity.Book;

public interface IBookService {
	
	
	public Book selectBookById(Integer id);
	
	
	public Integer deleteBookById(Integer id);
	
	public Integer insertBook(Book book);
   
	public Book selectBookWithUserById(Integer id);
	
	public Integer insertBatchBooks(List<Book> books);
	
	public void  addEntity(Book book);
}
