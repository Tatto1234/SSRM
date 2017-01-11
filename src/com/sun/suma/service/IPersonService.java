package com.sun.suma.service;

import java.util.List;

import com.sun.suma.entity.Person;
import com.sun.suma.entity.PersonGroupLink;

public interface IPersonService {
	
public Person selectperson(Long id);
	
	public Person selectpersonGroup(Long id);
	
	public int saveperson(Person person);
	
	public int saveRelativity(PersonGroupLink personGroupLink);
	
	public List<Person> selectAllperson();

}
