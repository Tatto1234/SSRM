package com.sun.suma.entity;

public class Book {
	
	private Integer id;
	
	private String bookName;
	
	private String publishing;
	
	private String year;
	
	private Integer userId;
	private  User user;

	public Integer getId() {
		return id;
	}
    
	
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPublishing() {
		return publishing;
	}

	public void setPublishing(String publishing) {
		this.publishing = publishing;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
     
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Book[id="+id+",  bookName="+bookName+",  publishing="+publishing+
				",  Year="+year+",  userId="+userId;
	}

}
