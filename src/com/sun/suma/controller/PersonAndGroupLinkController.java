package com.sun.suma.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.suma.entity.Group;
import com.sun.suma.entity.Person;
import com.sun.suma.entity.PersonGroupLink;
import com.sun.suma.service.IGroupService;
import com.sun.suma.service.IPersonService;

@Controller
@RequestMapping(value="/personandgroup")
public class PersonAndGroupLinkController {
	private static Logger log=LoggerFactory.getLogger(PersonAndGroupLinkController.class);
	@Resource 
	private IPersonService personService;
	@Resource
	private IGroupService groupService;
	
	@RequestMapping(value="/insertLink")
	public void insertPersonAndGroup(HttpServletResponse response)
	{
		
		Person person=new Person();
		person.setName("lisi");
		person.setPassword("222");
		Group group=new Group();
		group.setName("csu");
		group.setState(1);
		int pFlag=personService.saveperson(person);
		int gFlag=groupService.saveGroup(group);
		PersonGroupLink personGroupLink=new PersonGroupLink();
		personGroupLink.setGroup(group);
		personGroupLink.setPerson(person);
		int linkFlag=personService.saveRelativity(personGroupLink);
		if(pFlag>0)
		{
			log.info("");
		}
		else {
			log.info("");
		}
		if(gFlag>0)
		{
			log.info("");
		}
		else {
			log.info("");
		}
		if(linkFlag>0)
		{
			log.info("");
		}
		else {
			log.info("");
		}
	}
	
	@RequestMapping(value="/selectPersonAndGroup")
	public void selectPersonAndGroup()
	{
		Person person=personService.selectpersonGroup(Long.parseLong("1"));
		List<Group> groups=person.getGroup();
		log.info(""+person.getName()+" , "+person.getPassword());
		log.info("="+groups.size());
		log.info(""+groups.get(0).getName());
		log.info(""+groups.get(1).getName());
	}

}
