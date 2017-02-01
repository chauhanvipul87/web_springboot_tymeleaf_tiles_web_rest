package com.caonline.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	
	/* http://www.thymeleaf.org/eclipse-plugin-update-site/ */	
	/*@RequestMapping("/")
	public String testHelloWorld(ModelMap model){
		model.addAttribute("msg", "Hi There, Hello World!!!");
		return "index";
	}
	*/
	@RequestMapping("/login.html")
	public String testHelloWorld(ModelMap model){
		System.out.println("in hello world...................................................");
		model.addAttribute("msg", "Hi There, Hello World!!!");
		return "layout";
	}
	
	@RequestMapping(value = "/message.html", method = RequestMethod.GET)
    public String messages(Model model) {
		model.addAttribute("msg", "Hi There, Hello World!!!");
        return "message/index";
    }
 
}
