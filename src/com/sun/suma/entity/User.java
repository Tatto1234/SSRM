package com.sun.suma.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer age;
    
    private ArrayList<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		int size = (books==null)?0:books.size();
		return "User [id=" + id + ", userName=" + username + ", password="
				+ password + ", age=" + age + ",  bookount="+size+"]";
	}
    
    
}