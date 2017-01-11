package com.sun.suma.dao;

import com.sun.suma.entity.Group;

public interface IGroupDao {
	
	
	public Group selectGroupperson(Long id,String name);
	
	public Group selectGroup(Long id);
	
	public int saveGroup(Group group);
	
	

}
