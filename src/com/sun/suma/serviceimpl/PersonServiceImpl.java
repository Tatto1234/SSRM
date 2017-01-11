package com.sun.suma.serviceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sun.suma.dao.IPersonDao;
import com.sun.suma.entity.Person;
import com.sun.suma.entity.PersonGroupLink;
import com.sun.suma.service.IPersonService;

@Service("personService")
public class PersonServiceImpl  implements IPersonService{

	
	@Resource
	IPersonDao personDao;
	@Override
	public Person selectperson(Long id) {
		// TODO Auto-generated method stub
		return this.personDao.selectperson(id);
	}

	@Override
	public Person selectpersonGroup(Long id) {
		// TODO Auto-generated method stub
		return this.personDao.selectpersonGroup(id);
	}

	@Override
	public int saveperson(Person person) {
		// TODO Auto-generated method stub
		return this.personDao.saveperson(person);
	}

	@Override
	public int saveRelativity(PersonGroupLink personGroupLink) {
		// TODO Auto-generated method stub
		return this.personDao.saveRelativity(personGroupLink);
	}

	@Override
	public List<Person> selectAllperson() {
		// TODO Auto-generated method stub
		return this.personDao.selectAllperson();
	}

}
