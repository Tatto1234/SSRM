package com.sun.suma.serviceimpl;
import java.util.List;

import javax.annotation.Resource;  

import org.springframework.stereotype.Service;  

import com.sun.suma.dao.IUserDao;
import com.sun.suma.entity.User;
import com.sun.suma.service.IUserService;

  
@Service("userService")  
public class UserServiceImpl implements IUserService {  
    @Resource  
    private IUserDao userDao;  
    
    public User getUserById(int userId) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userId);  
    }

	@Override
	public int deleteUserById(int userId) {
		// TODO Auto-generated method stub
		return this.userDao.deleteByPrimaryKey(userId);
	}

	@Override
	public List<User> selectUserWithBookById(int id) {
		// TODO Auto-generated method stub
		return this.userDao.selectUserWithBookById(id);
	}  
  
}  
