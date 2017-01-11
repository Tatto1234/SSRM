package com.sun.suma.serviceimpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.suma.dao.IGroupDao;
import com.sun.suma.entity.Group;
import com.sun.suma.service.IGroupService;

@Service("groupService")
public class GroupServiceImpl implements IGroupService{

	
	@Resource
	IGroupDao groupDao;
	@Override
	public Group selectGroupperson(Long id,String name) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroupperson(id,name);
	}

	@Override
	public Group selectGroup(Long id) {
		// TODO Auto-generated method stub
		return this.groupDao.selectGroup(id);
	}

	@Override
	public int saveGroup(Group group) {
		// TODO Auto-generated method stub
		return this.groupDao.saveGroup(group);
	}

}
