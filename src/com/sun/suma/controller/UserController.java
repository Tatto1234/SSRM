package com.sun.suma.controller;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.suma.entity.User;
import com.sun.suma.service.IUserService;
  
  
@Controller  
// /user/**
public class UserController {  
	private static Logger log=LoggerFactory.getLogger(UserController.class);
	 @Resource  
	 private IUserService userService;     
    
//     /user/test?id=1
    @RequestMapping(value="/",method=RequestMethod.GET)  
    public String test(HttpServletRequest request,Model model){  
    	log.info("首页启动");
        return "index";  
    }  
    
    
    // /user/showUser?id=1
    @RequestMapping(value="/showUser",method=RequestMethod.GET)  
    public void toIndex(HttpServletRequest request,Model model){  
        int userId = Integer.parseInt(request.getParameter("id"));  
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);  
        log.debug(user.toString());
        model.addAttribute("user", user);  
          
    }  
    
 // /user/showUser2?id=1
    @RequestMapping(value="/showUser2",method=RequestMethod.GET)  
    public String toIndex2(@RequestParam("id") String id,Model model){  
        int userId = Integer.parseInt(id);  
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);  
        log.debug(user.toString());
        model.addAttribute("user", user);  
        return "showUser";  
    }  
    
    
    // /user/showUser3/{id}
    @RequestMapping(value="/showUser3/{id}",method=RequestMethod.GET)  
    public String toIndex3(@PathVariable("id")String id,Map<String, Object> model){  
        int userId = Integer.parseInt(id);  
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);  
        log.debug(user.toString());
        model.put("user", user);  
        return "showUser";  
    }  
    
 // /user/{id}
    @RequestMapping(value="/{id}",method=RequestMethod.GET)  
    public @ResponseBody User getUserInJson(@PathVariable String id,Map<String, Object> model){  
        int userId = Integer.parseInt(id);  
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);  
        log.info(user.toString());
        return user;  
    }  
    
    // /user/{id}
    @RequestMapping(value="/jsontype/{id}",method=RequestMethod.GET)  
    public ResponseEntity<User>  getUserInJson2(@PathVariable String id,Map<String, Object> model){  
        int userId = Integer.parseInt(id);  
        System.out.println("userId:"+userId);
        User user = this.userService.getUserById(userId);  
        log.info(user.toString());
        return new ResponseEntity<User>(user,HttpStatus.OK);  
    } 
    // /user/{id}
    @RequestMapping(value="/userWithBook/{id}",method=RequestMethod.GET)  
    public ResponseEntity<List<User>>  getUserWithBooksInJson(@PathVariable String id,Map<String, Object> model){  
        int userId = Integer.parseInt(id);  
        List<User> user = this.userService.selectUserWithBookById(userId);  
        log.info(user.toString());
        return new ResponseEntity<List<User>>(user,HttpStatus.OK);  
    } 
    @RequestMapping(value="/deleteUser")
    public ModelAndView deleteUserById(@RequestParam("id") String id)
    {
    	int flag=this.userService.deleteUserById(Integer.parseInt(id));
    	ModelAndView modelAndView=new ModelAndView("deleteUser");
    	if(flag==1)
    	{
    		log.info("删除成功！");
    		 return modelAndView.addObject("result","0000");
    	}
    	else {
    		log.info("删除失败！");
    		return modelAndView.addObject("result","1111");
		}
    	
    }

}  