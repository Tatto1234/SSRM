package com.sun.suma.service;  

import java.util.List;

import com.sun.suma.entity.User;

  
  
public interface IUserService {

    public User getUserById(int userId);  
  
    public int  deleteUserById(int userId);

    public List<User> selectUserWithBookById(int id);
}  