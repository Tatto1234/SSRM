package com.sun.suma.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.suma.entity.Book;
import com.sun.suma.entity.User;
import com.sun.suma.service.IBookService;
import com.sun.suma.service.IUserService;

@Controller
@RequestMapping(value="/book")
public class BookController {
	private static Logger log=LoggerFactory.getLogger(BookController.class);
	@Resource
	private IBookService bookService;
	@Resource 
	private IUserService userService;

	@RequestMapping(value="/insertBook")
	public void insertBook(HttpServletResponse response) throws IOException
	{
	    Book book=new Book();
	    book.setBookName("C++ book");
	    book.setPublishing("wuhan");
	    book.setYear("2016");
	    User user=userService.getUserById(4);
	    book.setUserId(user.getId());
	    int flag= bookService.insertBook(book);
	    if(flag>0)
	    {
	    	response.getWriter().println("数据插入成功");
	    	log.info("数据插入成功");
	    }
	    else {
	    	response.getWriter().println("数据插入失败！");
	    	log.info("数据插入失败！");
		}
	}
	
	@RequestMapping(value="/insertBatchBook")
	public void insertBatchBook(HttpServletResponse response) throws IOException
	{
	    Book book1=new Book();
	    book1.setBookName("C++ book");
	    book1.setPublishing("wuhan");
	    book1.setYear("2016");
	    book1.setUserId(1);
	    Book book2=new Book();
	    book2.setBookName("pahyon book");
	    book2.setPublishing("wuhan");
	    book2.setYear("2016");
	    book2.setUserId(1);
	    Book book3=new Book();
	    book3.setBookName("C# book");
	    book3.setPublishing("cdu");
	    book3.setYear("2016");
	    book3.setUserId(1);
	    List<Book> books=new ArrayList<Book>();
	    books.add(book1);
	    books.add(book2);
	    books.add(book3);
	    int flag= bookService.insertBatchBooks(books);
	    if(flag==books.size())
	    {
	    	response.getWriter().println("批量插入成功！");
	    	log.info("批量插入成功！");
	    }
	    else {
	    	response.getWriter().println("批量插入失败！");
	    	log.info("批量插入失败");
		}
	}
	
	 @RequestMapping(value="/bookWithUser/{id}",method=RequestMethod.GET)  
	    public ResponseEntity<Book>  getUserWithBooksInJson(@PathVariable String id,Map<String, Object> model){   
	        Book book = this.bookService.selectBookWithUserById(Integer.parseInt(id));
	        log.info("数据查询成功"+book.toString());
	        return new ResponseEntity<Book>(book,HttpStatus.OK);  
	    } 

}
