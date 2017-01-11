package com.sun.suma.entity;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class Person {
	
	private long id;

	private String name;

	private String password;

	private Date createTime;

	private List<Group> groups;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Group> getGroup() {
		return groups;
	}

	public void setGroup(List<Group> groups) {
		this.groups = groups;
	}

}
