package com.sun.suma.service;

import com.sun.suma.entity.Group;

public interface IGroupService {
	
public Group selectGroupperson(Long id,String name);
	
	public Group selectGroup(Long id);
	
	public int saveGroup(Group group);

}
